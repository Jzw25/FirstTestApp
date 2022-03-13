package com.example.myapplication.androidview;

public class AndroidViewTest {
   /**
    * android view 绘制流程：在activity创建时，attachactivity时window会与activity绑定，window与wms绑定，
    * (ViewRootImpl 是在WindowManagerGlobal 的addView里面创建的。  addView又是在ActivityThread 的
    * handleResumeActivity   里调用的。  而handleResumeActivity 又是在 handleLaunchActivity 里面调用的，
    * 并且是在 performLaunchActivity  之后才调用的。 也就是说Activity生命周期已经走完了 onCreate(), 和
    * onResume() 以后 才创建的 ViewRootImpl )，window就与dectorview绑定，在oncreate时调用setcontentview，内部解析xml，形成viwe，将view
    * 添加到dectorview的conten中去，decotrview分两个部分，一个上边为导航栏部分，一个为content部分。继承自fragmentlayout
    * 里面是一个linearlayout。photowindow是window实现类，dectorview就是与之绑定的。viewroot是连接器，实现viewrootlmpl
    * 连接 WindowManager（实现类 WindowManagerImpl） 和 DecorView；完成 View 的绘制流程（measure/layout/draw）
    */
}
