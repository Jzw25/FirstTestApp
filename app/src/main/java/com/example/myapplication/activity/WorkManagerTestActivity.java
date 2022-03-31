package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.OverwritingInputMerger;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;
import androidx.work.WorkerParameters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.ActivityWorkManagerTestBinding;
import com.example.myapplication.works.MyDataWork;
import com.example.myapplication.works.MyProgressWork;
import com.example.myapplication.works.MyWorkFour;
import com.example.myapplication.works.MyWorkOne;
import com.example.myapplication.works.MyWorkThree;
import com.example.myapplication.works.MyWorkTwo;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * workmanager测试
 * workmanager为后台工作管理类，只要满足约束条件后必会执行，除非卸载了app，无论app是否在前台都会执行，关机之后
 * 重启了也会执行，内部将存储与数据库中，匹配后调用dowork
 */
public class WorkManagerTestActivity extends AppCompatActivity {

    private ActivityWorkManagerTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkManagerTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        doWorkOne();
        doWorkTwo();
        doWorkFour();
        doDalayWork();
        doReTryWork();
        doDataWork();
        doOnlyWork();
        watchWork();
        cancelWork();
        watchDoingWoek();
        doLianWork();
    }

    /**
     * 链式工作
     */
    private void doLianWork() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkOne.class)
                /**
                 * OverwritingInputMerger 是默认的合并方法。如果合并过程中存在键冲突，键的最新值将覆盖生成的
                 * 输出数据中的所有先前版本。
                 * 即在outputdata中只保留后面的值，前面的值没了，如：{“name" , "zs"},{“name" , "ls"}
                 * 最后为{“name" , "ls"}
                 */
                .setInputMerger(OverwritingInputMerger.class)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest1 = new OneTimeWorkRequest.Builder(MyWorkTwo.class)
                /**
                 * ArrayCreatingInputMerger 将每个键与数组配对。如果每个键都是唯一的，您会得到一系列一元数组
                 * 如：{“name" , "zs"},{“name" , "ls"}最后为{“name" , "zs,ls"}
                 */
                .setInputMerger(ArrayCreatingInputMerger.class)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MyWorkThree.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(MyWorkFour.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(MyDataWork.class).build();

        WorkManager workManager = WorkManager.getInstance(this);
        //先运行123,如果添加了 OneTimeWorkRequest 实例的 List，这些请求可能会并行运行
        workManager.beginWith(Arrays.asList(oneTimeWorkRequest1,oneTimeWorkRequest2,oneTimeWorkRequest3))
                //123完成后运行4
                .then(oneTimeWorkRequest4)
                //4完成后运行
                .then(oneTimeWorkRequest)
                .enqueue();

        /**
         * 双链式调用
         * 1 3
         * 2 4
         * 5
         */
        WorkContinuation workContinuation = WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).then(oneTimeWorkRequest1);
        WorkContinuation workContinuation1 = WorkManager.getInstance(this).beginWith(oneTimeWorkRequest2).then(oneTimeWorkRequest3);
        WorkContinuation continuation = WorkContinuation.combine(Arrays.asList(workContinuation, workContinuation1)).then(oneTimeWorkRequest4);
        continuation.enqueue();


    }

    /**
     * 观察中间进度
     *
     * WorkManager 2.3.0-alpha01 为设置和观察工作器的中间进度添加了一流的支持。如果应用在前台运行时，工作器保
     * 持运行状态，那么也可以使用返回 WorkInfo 的 LiveData 的 API 向用户显示此信息。
     *
     * ListenableWorker 现在支持 setProgressAsync() API，此类 API 允许保留中间进度。借助这些 API，开发者能
     * 够设置可通过界面观察到的中间进度。进度由 Data 类型表示，这是一个可序列化的属性容器（类似于 input 和
     * output，并且受到相同的限制）。
     *
     * 只有在 ListenableWorker 运行时才能观察到和更新进度信息。如果尝试在 ListenableWorker 完成执行后在其中
     * 设置进度，则将会被忽略。您还可以使用 getWorkInfoBy…() 或 getWorkInfoBy…LiveData() 方法来观察进度信
     * 息。这两个方法会返回 WorkInfo 的实例，后者有一个返回 Data 的新 getProgress() 方法。
     *
     * 观察进度信息也很简单。您可以使用 getWorkInfoBy…() 或 getWorkInfoBy…LiveData() 方法，并引用 WorkInfo。
     */
    private void watchDoingWoek() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyProgressWork.class).build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(oneTimeWorkRequest);
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo!=null){
                    Data outputData = workInfo.getOutputData();
                    int progress = outputData.getInt("progress", 0);
                    //do sothing with 进度
                }
            }
        });
    }

    /**
     * 取消和停止工作
     * 如果您不再需要运行先前加入队列的工作，则可以要求将其取消。您可以按工作的 name、id 或与其关联的 tag 取消工作。
     */
    private void cancelWork() {
        MyWorkOne workOne = new MyWorkOne(this,null);
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.cancelAllWork();
        //tag 注意：cancelAllWorkByTag(String) 会取消具有给定标记的所有工作。
        workManager.cancelAllWorkByTag("");
        //id
        workManager.cancelWorkById(workOne.getId());
        //name
        workManager.cancelUniqueWork("");

        /**
         * WorkManager 会在后台检查工作的 State。如果工作已经完成，系统不会执行任何操作。否则，工作的状态会更改为
         * CANCELLED，之后就不会运行这个工作。任何依赖于此工作的 WorkRequest 作业也将变为 CANCELLED。
         *
         * 目前，RUNNING 可收到对 ListenableWorker.onStopped() 的调用。如需执行任何清理操作，请替换此方法。
         */

        /**
         * 停止正在运行的工作器
         * 正在运行的 Worker 可能会由于以下几种原因而停止运行：
         *
         * 您明确要求取消它（例如，通过调用 WorkManager.cancelWorkById(UUID) 取消）。
         * 如果是唯一工作，您明确地将 ExistingWorkPolicy 为 REPLACE 的新 WorkRequest 加入到了队列中。旧的 WorkRequest 会立即被视为已取消。
         * 您的工作约束条件已不再满足。
         * 系统出于某种原因指示您的应用停止工作。如果超过 10 分钟的执行期限，可能会发生这种情况。该工作会调度为在稍后重试。
         * 在这些情况下，您的工作器会停止。
         *
         * 您应该合作地取消正在进行的任何工作，并释放您的工作器保留的所有资源。例如，此时应该关闭所打开的数据库和文件句柄。有两种机制可让您了解工作器何时停止。
         *
         * onStopped() 回调
         * 在您的工作器停止后，WorkManager 会立即调用 ListenableWorker.onStopped()。替换此方法可关闭您可能保留的所有资源。
         *
         * isStopped() 属性
         * 您可以调用 ListenableWorker.isStopped() 方法以检查工作器是否已停止。如果您在工作器执行长时间运行的操作或重复操作，您应经常检查此属性，并尽快将其用作停止工作的信号。
         *
         * 注意：WorkManager 会忽略已收到 onStop 信号的工作器所设置的 Result，因为工作器已被视为停止。
         */

    }

    /**
     * 观察工作
     * 在将工作加入队列后，您可以随时按其 name、id 或与其关联的 tag 在 WorkManager 中进行查询，以检查其状态。
     */
    private void watchWork() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkOne.class)
                /**
                 * 标记工作
                 * WorkManager.cancelAllWorkByTag(String) 会取消带有特定标记的所有工作请求，WorkManager.
                 * getWorkInfosByTag(String) 会返回一个 WorkInfo 对象列表，该列表可用于确定当前工作状态。
                 */
                .addTag("watchwork")
                .build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(oneTimeWorkRequest);
        /**
         * 该查询会返回 WorkInfo 对象的 ListenableFuture，该值包含工作的 id、其标记、其当前的 State 以及通过 Result.success(outputData) 设置的任何输出数据。
         *
         * 利用每个方法的 LiveData 变种，您可以通过注册监听器来观察 WorkInfo 的变化。例如，如果您想要在某项工作成功完成后向用户显示消息，您可以进行如下设置：
         */
        ListenableFuture<WorkInfo> workInfoById = workManager.getWorkInfoById(oneTimeWorkRequest.getId());
        ListenableFuture<List<WorkInfo>> workInfosForUniqueWork = workManager.getWorkInfosForUniqueWork("name");
        ListenableFuture<List<WorkInfo>> tag = workManager.getWorkInfosByTag("tag");
        try {
            WorkInfo workInfo = workInfoById.get();
            workInfo.getId();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LiveData<WorkInfo> workInfoByIdLiveData = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId());
        workInfoByIdLiveData.observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() != null &&
                        workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    Log.d(MyWorkOne.TAG, "onChanged: state change success");
                }
            }
        });

        /**
         * 复杂的工作查询
         * WorkManager 2.4.0 及更高版本支持使用 WorkQuery 对象对已加入队列的作业进行复杂查询。WorkQuery
         * 支持按工作的标记、状态和唯一工作名称的组合进行查询。
         * fromTags,fromid,fromStates,fromUniqueWorkNames
         *
         * 以下示例说明了如何查找带有“syncTag”标记、处于 FAILED 或 CANCELLED 状态，且唯一工作名称为“preProcess”或“sync”的所有工作。
         */

        WorkQuery watchwork = WorkQuery.Builder.fromTags(Arrays.asList("watchwork"))
                .addStates(Arrays.asList(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
                .addUniqueWorkNames(Arrays.asList("preProcess", "sync"))
                .build();
        /**
         * WorkQuery 中的每个组件（标记、状态或名称）与其他组件都是 AND 逻辑关系。组件中的每个值都是 OR 逻辑关系。
         * 例如：<em>(name1 OR name2 OR ...) AND (tag1 OR tag2 OR ...) AND (state1 OR state2 OR ...)</em>
         *
         * WorkQuery 也适用于等效的 LiveData 方法 getWorkInfosLiveData()。
         */
        ListenableFuture<List<WorkInfo>> workInfos = workManager.getWorkInfos(watchwork);
        LiveData<List<WorkInfo>> workInfosLiveData = workManager.getWorkInfosLiveData(watchwork);

    }

    /**
     * 唯一工作
     * 唯一工作是一个很实用的概念，可确保同一时刻只有一个具有特定名称的工作实例。与 ID 不同的是，唯一名称是人类
     * 可读的，由开发者指定，而不是由 WorkManager 自动生成。与标记不同，唯一名称仅与一个工作实例相关联。
     * 唯一工作既可用于一次性工作，也可用于定期工作。您可以通过调用以下方法之一创建唯一工作序列，具体取决于您是调度重复工作还是一次性工作。
     *
     * WorkManager.enqueueUniqueWork()（用于一次性工作）
     * WorkManager.enqueueUniquePeriodicWork()（用于定期工作）
     * 这两种方法都接受 3 个参数：
     *
     * uniqueWorkName - 用于唯一标识工作请求的 String。
     * existingWorkPolicy - 此 enum 可告知 WorkManager：如果已有使用该名称且尚未完成的唯一工作链，应执行什么操作。如需了解详情，请参阅冲突解决政策。
     * work - 要调度的 WorkRequest。
     * 借助唯一工作，我们可以解决前面提到的重复调度问题。即重复调用相同的work类
     *
     * 对于一次性工作，您需要提供一个 ExistingWorkPolicy，它支持用于处理冲突的 4 个选项。
     *
     * REPLACE：用新工作替换现有工作。此选项将取消现有工作。
     * KEEP：保留现有工作，并忽略新工作。
     * APPEND：将新工作附加到现有工作的末尾。此政策将导致您的新工作链接到现有工作，在现有工作完成后运行。
     * 现有工作将成为新工作的先决条件。如果现有工作变为 CANCELLED 或 FAILED 状态，新工作也会变为 CANCELLED 或 FAILED。如果您希望无论现有工作的状态如何都运行新工作，请改用 APPEND_OR_REPLACE。
     *
     * APPEND_OR_REPLACE 函数类似于 APPEND，不过它并不依赖于先决条件工作状态。即使现有工作变为 CANCELLED 或 FAILED 状态，新工作仍会运行。
     * 对于定期工作，您需要提供一个 ExistingPeriodicWorkPolicy，它支持 REPLACE 和 KEEP 这两个选项。这些选项的功能与其对应的 ExistingWorkPolicy 功能相同。
     */
    private void doOnlyWork() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkOne.class)
                .build();
        WorkManager.getInstance(this).enqueueUniqueWork("workoneid", ExistingWorkPolicy.APPEND,oneTimeWorkRequest);

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorkOne.class, 20, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("workoneid", ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest);
    }

    private void doDataWork() {
        @SuppressLint("RestrictedApi") Data data = new Data.Builder().put("data", "ashdahsd").build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyDataWork.class)
                .setInputData(data)
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    /**
     * 重试和退避政策
     * 如果您需要让 WorkManager 重试工作，可以从工作器返回 Result.retry()。然后，系统将根据退避延迟时间和退避政策重新调度工作。
     *
     * 退避延迟时间指定了首次尝试后重试工作前的最短等待时间。此值不能超过 10 秒（或 MIN_BACKOFF_MILLIS）。
     *
     * 退避政策定义了在后续重试过程中，退避延迟时间随时间以怎样的方式增长。WorkManager 支持 2 个退避政策，即 LINEAR 和 EXPONENTIAL。
     *
     * 每个工作请求都有退避政策和退避延迟时间。默认政策是 EXPONENTIAL，延迟时间为 10 秒，但您可以在工作请求配置中替换此设置。
     */
    private void doReTryWork() {
        /**
         * 在本示例中，最短退避延迟时间设置为允许的最小值，即 10 秒。由于政策为 LINEAR，每次尝试重试时，重试间隔
         * 都会增加约 10 秒。例如，第一次运行以 Result.retry() 结束并在 10 秒后重试；然后，如果工作在后续尝试
         * 后继续返回 Result.retry()，那么接下来会在 20 秒、30 秒、40 秒后重试，以此类推。如果退避政策设置为
         * EXPONENTIAL，那么重试时长序列将接近 20、40、80 秒，以此类推。
         * 注意：退避延迟时间不精确，在两次重试之间可能会有几秒钟的差异，但绝不会低于配置中指定的初始退避延迟时间。
         */
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkFour.class)
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 延期工作
     * 执行工作器的确切时间还取决于 WorkRequest 中使用的约束和系统优化方式。WorkManager 经过设计，能够在满足
     * 这些约束的情况下提供可能的最佳行为。
     */
    private void doDalayWork() {
        /**
         * 为 OneTimeWorkRequest 设置初始延迟时间，您也可以为 PeriodicWorkRequest 设置初始延迟时间。
         * 在这种情况下，定期工作只有首次运行时会延迟。
         */
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkOne.class)
                .setInitialDelay(10, TimeUnit.MINUTES)
                .build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorkFour.class, 20, TimeUnit.MINUTES)
                .setInitialDelay(5,TimeUnit.MINUTES)
                .build();
    }

    private void doWorkFour() {
        /**
         * 定期工作,间隔15min
         * 时间间隔定义为两次重复执行之间的最短时间。工作器的确切执行时间取决于您在 WorkRequest 对象中设置的约束以及系统执行的优化。
         * 注意：可以定义的最短重复间隔是 15 分钟（与 JobScheduler API 相同）。
         */
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorkFour.class, 15, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);

        /**
         * 灵活的运行间隔
         * 每小时的最后 15 分钟内运行的定期工作
         * 重复间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS(15min)，而灵活间隔必须
         * 大于或等于 PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS(5min)。
         */
        PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest.Builder(MyWorkFour.class, 1, TimeUnit.HOURS, 15, TimeUnit.MINUTES);

        /**
         * 约束对定期工作的影响
         * 您可以对定期工作设置约束。例如，您可以为工作请求添加约束，以便工作仅在用户设备充电时运行。在这种情况下，
         * 除非满足约束条件，否则即使过了定义的重复间隔，PeriodicWorkRequest 也不会运行。这可能会导致工作在某
         * 次运行时出现延迟，甚至会因在相应间隔内未满足条件而被跳过。
         */

    }

    private void doWorkTwo() {
        /**
         * 设置工作约束
         * NetworkType	约束运行工作所需的网络类型。例如 Wi-Fi (UNMETERED)。
         * BatteryNotLow	如果设置为 true，那么当设备处于“电量不足模式”时，工作不会运行。
         * RequiresCharging	如果设置为 true，那么工作只能在设备充电时运行。
         * DeviceIdle	如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。在运行批量操作时，此约束会非常有用；若是不用此约束，批量操作可能会降低用户设备上正在积极运行的其他应用的性能。
         * StorageNotLow	如果设置为 true，那么当用户设备上的存储空间不足时，工作不会运行。
         */
        Constraints.Builder builder = new Constraints.Builder();
        Constraints constraints = builder.setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresStorageNotLow(true)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkTwo.class)
                /**
                 * 设置加急任务
                 * 我们初始化 OneTimeWorkRequest 的实例并对其调用 setExpedited()。然后，此请求就会变成加急工作。
                 * 如果配额允许，它将立即开始在后台运行。
                 * OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST，这会导致作业作为普通工作请求运行。上述代码段演示了此操作。
                 * OutOfQuotaPolicy.DROP_WORK_REQUEST，这会在配额不足时导致请求取消。
                 */
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(constraints)
                .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(oneTimeWorkRequest);


    }

    /**
     * WorkRequest
     * 代表一项任务请求。一个 WorkRequest对象至少要指定一个Worker类。同时，还可以向WorkRequest对象添加 ，指定
     * 任务应运行的环境等。每个人WorkRequest都有一个自动生成的唯一ID， 可以使用该ID来执行诸如取消排队的任务或获
     * 取任务状态等操作。WorkRequest是一个抽象类; 有两个直接子类 OneTimeWorkRequest和 PeriodicWorkRequest。
     * 与WorkerRequest相关的有如下两个类：
     *
     * ① WorkRequest.Builder：用于创建WorkRequest对象的助手类 ，其有两个子类OneTimeWorkRequest.Builder和
     * PeriodicWorkRequest.Builder，分别对应两者创建上述两种WorkerRequest。
     *
     * ② Constraints：指定任务运行时的限制（例如，“仅在连接到网络时才能运行”）。可以通过 Constraints.Builder
     * 来创建该对象，并在调用WorkRequest.Builder的build()方法之前，将其传递 给WorkerRequest。
     */
    private void doWorkOne() {
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(MyWorkOne.class);
                OneTimeWorkRequest oneTimeWorkRequest = builder.build();

                //调度一次性工作
                //对于无需额外配置的简单工作，请使用静态方法 from
                OneTimeWorkRequest from = OneTimeWorkRequest.from(MyWorkOne.class);
                WorkManager.getInstance(v.getContext()).enqueue(from);

                /**
                 *  WorkManager
                 * 这个类用来安排和管理工作请求。前面创建的WorkRequest 对象通过WorkManager来安排的顺序。
                 * WorkManager调度任务的时候会分散系统资源，做好类似负载均衡的操作，同时会遵循前面设置的对任务的约束条件。
                 */
                WorkManager.getInstance(v.getContext()).enqueue(oneTimeWorkRequest);
            }
        });
    }

    /**
     *  一次性工作的状态 图在hdpi下onetimework
     * 对于 one-time 工作请求，工作的初始状态为 ENQUEUED。
     *
     * 在 ENQUEUED 状态下，您的工作会在满足其 Constraints 和初始延迟计时要求后立即运行。接下来，该工作会转为
     * RUNNING 状态，然后可能会根据工作的结果转为 SUCCEEDED、FAILED 状态；或者，如果结果是 retry，它可能会回到
     * ENQUEUED 状态。在此过程中，随时都可以取消工作，取消后工作将进入 CANCELLED 状态。
     * SUCCEEDED、FAILED 和 CANCELLED 均表示此工作的终止状态。如果您的工作处于上述任何状态，WorkInfo.State.isFinished() 都将返回 true。
     */

    /**
     * 定期工作的状态
     * 成功和失败状态仅适用于一次性工作和链式工作。定期工作只有一个终止状态 CANCELLED。这是因为定期工作永远不会
     * 结束。每次运行后，无论结果如何，系统都会重新对其进行调度。图在hdpi下chongfuwork
     */

    //BLOCKED 状态
    //还有一种我们尚未提到的最终状态，那就是 BLOCKED。此状态适用于一系列已编排的工作，或者说工作链。链接工作中介绍了工作链及其状态图。
}