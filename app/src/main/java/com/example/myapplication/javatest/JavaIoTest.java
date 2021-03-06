package com.example.myapplication.javatest;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * io测试
 */
public class JavaIoTest {

    public static final String TAG = JavaIoTest.class.toString();

    public void outPutTest(){
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            //第一个是文件名,第二个参数true，设置为写入的数据拼接在尾部
            fileOutputStream = new FileOutputStream("a.txt", true);
            fileInputStream = new FileInputStream("b.txt");
            /*
             * 创建字节输出流对象了做了几件事情：
             * A:调用系统功能去创建文件
             * B:创建outputStream对象
             * C:把foutputStream对象指向这个文件
             */
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes))!=-1){
                fileOutputStream.write(bytes,0,len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inputTest(){
        /**
         * public int read()
         * public int read(byte[] b)
         * 第一个read是读一个字节，第二个read是读一个字节数组。
         */
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("a.txt");
            int len;
            byte[] bys = new byte[1024];
            while ((len = fileInputStream.read(bys))!=-1){
                Log.d(TAG, "inputTest: " + new String(bys,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 成员方法与字节流基本一样，字节缓冲流的作用就是提高输入输出的效率。
     * 构造方法可以指定缓冲区的大小，但是我们一般用不上，因为默认缓冲区大小就足够了。
     * 为什么不传递一个具体的文件或者文件路径，而是传递一个OutputStream对象呢?原因很简单，字节缓冲区流仅仅提供
     * 缓冲区，为高效而设计的。但是呢，真正的读写操作还得靠基本的流对象实现。
     */
    public void bufferedStreamTest(){
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream("a.txt"));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("b.txt"));

            int len;
            byte[] bytes = new byte[1024];
            while ((len = bufferedInputStream.read(bytes))!=-1){
                bufferedOutputStream.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedInputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * //一个字节一个字节的复制，耗时22697毫秒
     *     public static  void  fun() throws IOException {
     *         FileInputStream fis = new FileInputStream("F:\\汤包\\慕课大巴\\modern-java.pdf");
     *         FileOutputStream fos = new FileOutputStream("E:\\modern-java.pdf");
     *         int by = 0;
     *         while ((by=fis.read()) != -1) {
     *             fos.write(by);
     *         }
     *         fis.close();
     *         fos.close();
     *     }
     *     //1024字节数组复制 耗时63毫秒
     *     public  static void  fun1() throws IOException {
     *         FileInputStream fis = new FileInputStream("F:\\汤包\\慕课大巴\\modern-java.pdf");
     *         FileOutputStream fos = new FileOutputStream("E:\\modern-java.pdf");
     *         int len = 0;
     *         byte[] bytes =new byte[1024];
     *         while ((len=fis.read(bytes)) != -1) {
     *             fos.write(bytes,0,len);
     *         }
     *         fis.close();
     *         fos.close();
     *     }
     *     // 一个字节一个字节复制，但是用了缓冲流 耗时64毫秒
     *     public static   void  fun2() throws IOException {
     *         BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E:\\modern-java.pdf"));
     *         BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\汤包\\慕课大巴\\modern-java.pdf"));
     *         int by = 0;
     *         while ((by=bis.read()) != -1) {
     *             bos.write(by);
     *         }
     *         bis.close();
     *         bos.close();
     *     }
     *     // 1024字节数组复制并用了缓冲流 耗时7毫秒
     *     public  static void  fun3() throws IOException {
     *         BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E:\\modern-java.pdf"));
     *         BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\汤包\\慕课大巴\\modern-java.pdf"));
     *         int len = 0;
     *         byte[] bytes =new byte[1024];
     *         while ((len=bis.read(bytes)) != -1) {
     *             bos.write(bytes,0,len);
     *         }
     *         bis.close();
     *         bos.close();
     *     }
     *
     *     public static void main(String args[]) throws IOException {
     *         long t1 = System.currentTimeMillis();
     *         fun3();
     *         long t2 = System.currentTimeMillis();
     *         System.out.println(t2-t1);
     *     }
     */

    /**
     * 将字节流包装成字符流 InputStreamReader
     * InputStreamReade 类用于包装 InputStream ，从而将基于字节的输入流转换为基于字符的 Reader 。 换句话说，
     * InputStreamReader 将 InputStream 的字节解释为文本而不是数字数据，是字节流通向字符流的桥梁。
     * 为了达到最高效率，可要考虑在 BufferedReader 内包装 InputStreamReader。例如：
     * BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
     *
     * 通常用于从文件（或网络连接）中读取字符，其中字节表示文本。 例如，一个文本文件，其中字符编码为 UTF-8 。
     * 可以使用InputStreamReader 来包装 FileInputStream 以读取此类文件。
     * 一个示例如下：
     */
    public void testInputStrwamReader(){
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(new FileInputStream("a.txt"));
            int date = reader.read();
            while (date!=-1){
                char c = (char) date;
                Log.d(TAG, "testInputStrwamReader: " + c);
                date = reader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定字符集
     * 底层 InputStream 中的字符将使用某些字符编码进行编码。 此字符编码称为字符集，Charset。 两种常用字符集是
     * ASCII 和 UTF8（在某些情况下为UTF-16）。
     *默认字符集可能因为环境不同而不同，所以建议告诉 InputStreamReader 实例 InputStream 中的字符用什么字符集
     * 进行编码。 这可以在 InputStreamReader 构造函数中指定，可以只提供字符集的名字字符串，在底层会调用
     * Charset.forName("UTF-8") 进行转换的。 以下是设置 InputStreamReader 使用的字符集的示例：
     * InputStream inputStream = new FileInputStream("D:\\test\\1.txt");
     * Reader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
     */

    //同样建议使用 try with resources 来关闭流。同样，只要关闭最外层的包装流，里面的流会被系统自动关闭。
    public void testTryWithResources(){
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream("a.txt"))) {
            int date ;
            while (( date = reader.read())!=-1){
                char c = (char) date;
                Log.d(TAG, "testTryWithResources: c:" + c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tryOutputStreamWriterTest(){
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("a.txt"))) {
            writer.write("this is OutputStreamWriter test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读写文件 FileReader/FileWriter
     * FileReader
     * FileReader类使得可以将文件的内容作为字符流读取。 它的工作方式与 FileInputStream 非常相似，只是
     * FileInputStream 读取字节，而 FileReader 读取字符。 换句话说，FileReader 旨在读取文本。 取决于字符编
     * 码方案，一个字符可以对应于一个或多个字节。
     *
     * FileReader 假定您要使用运行应用程序的计算机的默认字符编码来解码文件中的字节。 这可能并不总是你想要的，
     * 但是不能改变它！
     * 如果要指定其他字符解码方案，请不要使用 FileReader 。 而是在 FileInputStream 上使用 InputStreamReader
     * 。 InputStreamReader 允许您指定在读取基础文件中的字节时使用的字符编码方案。
     *
     * FileWriter
     * FileWriter 类可以将字符写入文件。 在这方面它的工作原理与 FileOutputStream 非常相似，只是
     * FileOutputStream 是基于字节的，而 FileWriter 是基于字符的。 换句话说，FileWriter 用于写文本。
     * 一个字符可以对应于一个或多个字节，这取决于使用的字符编码方案。
     * 创建 FileWriter 时，可以决定是否要覆盖具有相同名称的任何现有文件，或者只是要追加内容到现有文件。
     * 可以通过选择使用的 FileWriter 构造函数来决定。
     *
     * FileWriter(File file, boolean append): 根据给定的 File 对象构造一个 FileWriter 对象。 示例如下：
     * Writer fileWriter = new FileWriter("c:\\data\\output.txt", true);  // 追加模式
     * Writer fileWriter = new FileWriter("c:\\data\\output.txt", false); // 默认情况，直接覆盖原文件
     *
     * 注意，只要成功 new 了一个 FileWriter 对象，没有指定是追加模式的话，那不管有没有调用 write() 方法，都会清空文件内容。
     */

    public void tryFileWRTest(){
        File file = new File("a.txt");
        FileWriter fileWriter = null;
        FileWriter fileWriter1 = null;
        FileReader fileReader = null;
        try {
            // 默认是覆盖模式
            fileWriter = new FileWriter(file);
            fileWriter.write("this is nonmal fileWriter test");

            fileWriter1 = new FileWriter(file,true);
            fileWriter1.write("this is append filewriter test");

            fileReader = new FileReader(file);
            int date;
            while ((date = fileReader.read())!=-1){
                char c = (char) date;
                Log.d(TAG, "tryFileWRTest: c:" + c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            file = null;
            try {
                fileWriter.close();
                fileWriter1.close();
                fileWriter1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PipedReader/PipedWriter
     * 读写管道 PipedReader 和 PipedWriter
     * PipedReader 类使得可以将管道的内容作为字符流读取。 因此它的工作方式与 PipedInputStream 非常相似，只是
     * PipedInputStream 是基于字节的，而不是基于字符的。 换句话说，PipedReader 旨在读取文本。PipedWriter 同理。
     *
     * 构造器
     * 方法	描述
     * PipedReader()	创建尚未连接的 PipedReader。
     * PipedReader(int pipeSize)	创建一个尚未连接的 PipedReader，并对管道缓冲区使用指定的管道大小。
     * PipedReader(PipedWriter src)	创建直接连接到传送 PipedWriter src 的 PipedReader。
     * PipedReader(PipedWriter src, int pipeSize)	创建一个 PipedReader，使其连接到管道 writer src，并对管道缓冲区使用指定的管道大小。
     * PipedWriter()	创建一个尚未连接到传送 reader 的传送 writer。
     * PipedWriter(PipedReader snk)	创建传送 writer，使其连接到指定的传送 reader。
     *
     * 读写之前，必须先建立连接
     * PipedReader 必须连接到 PipedWriter 才可以读 ，PipedWriter 也必须始终连接到 PipedReader 才可以写。就是说读写之前，必须先建立连接，有两种方式可以建立连接。
     *
     * 通过构造器创建，伪代码如 Piped piped1 = new Piped(piped2);
     * 调用其中一个的 connect() 方法，伪代码如 Piped1.connect(Piped2);
     * 并且通常，PipedReader 和 PipedWriter 由不同的线程使用。 注意只有一个 PipedReader 可以连接到同一个 PipedWriter ，一个示例如下：
     */

    public void tryPipeWRTest(){
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = null;
        try {
            pipedReader = new PipedReader(pipedWriter);
            pipedWriter.write("this is pipewriter test");

            int date;
            while ((date = pipedReader.read())!=-1){
                char c = (char) date;
                Log.d(TAG, "tryPipeWRTest: c : "+c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                pipedWriter.close();
                pipedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读写字符数组 CharArrayReader/CharArrayWriter
     * ByteArrayInputStream/ByteArrayOutputStream 是对字节数组处理，CharArrayReader/CharArrayWriter
     * 则是对字符数组进行处理，其用法是基本一致的，所以这里略微带过。
     *
     * CharArrayReader
     * CharArrayReader 类可以将 char 数组的内容作为字符流读取。
     * 只需将 char 数组包装在 CharArrayReader 中就可以很方便的生成一个 Reader 对象。
     *
     * CharArrayWriter
     * CharArrayWriter 类可以通过 Writer 方法（CharArrayWriter是Writer的子类）编写字符，并将写入的字符转换为 char 数组。
     * 在写入所有字符时，CharArrayWriter 上调用 toCharArray() 能很方便的生成一个字符数组。
     *
     * 两个类的构造函数：
     * 方法	描述
     * CharArrayReader(char[] buf)	根据指定的 char 数组创建一个 CharArrayReader
     * CharArrayReader(char[] buf, int offset, int length)	根据指定的 char 数组创建一个 CharArrayReader
     * CharArrayWriter()	创建一个新的 CharArrayWriter ，默认缓冲区大小为 32
     * CharArrayWriter(int initialSize)	创建一个具有指定初始缓冲区大小的新 CharArrayWriter
     * 注意：设置初始大小不会阻止 CharArrayWriter 存储比初始大小更多的字符。 如果写入的字符数超过了初始 char
     * 数组的大小，则会创建一个新的更大的 char 数组，并将所有字符复制到新数组中。
     *
     * 一个使用实例如下：
     */

    public void tryCharArrayWRTest(){
        CharArrayWriter writer = new CharArrayWriter();
        CharArrayReader reader = null;
        writer.append('H');
        try {
            writer.write("ello ".toCharArray());
            writer.write("World");
            char[] chars = writer.toCharArray();

            reader = new CharArrayReader(chars);
            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data); // Hello World
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
            reader.close();
        }

    }

    /**
     * Serializable:
     * 接口的作用是实现序列化,序列化：对象的寿命通常随着生成该对象的程序的终止而终止，有时候需要把在内存中的各种
     * 对象的状态（也就是实例变量，不是方法）保存下来，并且可以在需要时再将对象恢复。虽然你可以用你自己的各种各样
     * 的方法来保存对象的状态，但是Java给你提供一种应该比你自己的好的保存对象状态的机制，那就是序列化。
     *
     * 总结：Java 序列化技术可以使你将一个对象的状态写入一个Byte 流里（系列化），并且可以从其它地方把该Byte
     * 流里的数据读出来（反序列化）。
     *
     * 系列化的用途
     * 想把的内存中的对象状态保存到一个文件中或者数据库中时候
     * 想把对象通过网络进行传播的时候
     *
     * 如何序列化：只要一个类实现Serializable接口，那么这个类就可以序列化了。一个类声明了Serializable，即让
     * jvm自动帮助做序列化，此为一个标识
     *
     * 关于Serializable的使用，有几点需要说明：
     * 1 Serializable只是一个接口，本身没有任何实现
     * 2 对象的反序列化并没有调用对象的任何构造方法
     * 3 serialVersionUID是用于记录文件版本信息的，最好能够自定义。否则，系统会自动生成一个serialVersionUID，
     * 文件或者对象的任何改变，都会改变serialVersionUID，导致反序列化的失败，如果自定义就没有这个问题。
     * 4 如果某个属性不想实现序列化，可以采用transient修饰
     * 5 Serializable的系统实现是采用ObjectInputStream和ObjectOutputStream实现的，这也是为什么调用
     * ObjectInputStream和ObjectOutputStream时，需要对应的类实现Serializable接口。
     *
     * 下面是一个序列化文件保存后的十六进制文件：
     * phone.txt文件中的内容如下：
     *
     * aced 0005 7372 0011 636f 6d2e 6578 616d
     * 706c 652e 5068 6f6e 6551 4868 16d4 8afd
     * 8702 0002 4c00 0761 6464 7265 7373 7400
     * 124c 6a61 7661 2f6c 616e 672f 5374 7269
     * 6e67 3b4c 0004 6e61 6d65 7100 7e00 0178
     * 7074 0007 6265 696a 696e 6774 0008 7a68
     * 616e 6773 616e
     * 如上是以byte的形式打开，下面来分析一下各个数据代表了什么意思（一下数据均为16进制，省略了ox前缀）：
     * aced：STREAM_MAGIC，声明使用了序列化协议
     * 0005：STREAM_VERSION，序列化协议版本
     * 73：TC_OBJECT，声明这是一个新的对象
     * 72：TC_CLASSDESC，声明这里开始一个新Class
     * 0011：Class名字的长度
     * 636f 6d2e 6578 616d 706c 652e 5068 6f6e 65：类名，com.example.Phone
     * 51 4868 16d4 8afd 87：SerialVersionUID, 序列化ID，如果没有指定， 则会由算法随机生成一个8byte的ID。
     * 02：标记号，该值声明该对象支持序列化
     * 0002：该类所包含的域个数
     * 4c：L
     * 00 07：域名字的长度
     * 61 6464 7265 7373：address
     * 74：TC_STRING，代表一个new String，用String来引用对象
     * 00 12：长度
     * 4c：L
     * 6a61 7661 2f6c 616e 672f 5374 7269 6e67 3b4c：java/lang/String;L
     * 0004：域名字的长度
     * 6e61 6d65：name
     * 7100 7e00 01：待确认
     * 78：TC_ENDBLOCKDATA对象块结束的标志
     * 70：没有超类
     * 74：TC_STRING
     * 0007：address字段值的长度
     * 6265 696a 696e 67：address字段的值beijing
     * 74：TC_STRING
     * 0008：name字段值的长度
     * 7a68 616e 6773 616e : name字段的值zhangsan
     * 文件中的内容相当丰富，仅仅凭这些内容，已经可以完整的复原Phone实例存储前的状态。
     * 其实，如上过程，就是Java 序列化和反序列化的一个简单的实现。
     * Serializable也能够实现自定义序列化过程。
     *
     *
     * Java 的序列化步骤与数据结构分析
     * 序列化算法一般会按步骤做如下事情：
     *
     * 将对象实例相关的类元数据输出。
     * 递归地输出类的超类描述直到不再有超类。
     * 类元数据完了以后，开始从最顶层的超类开始输出对象实例的实际数据值。
     * 从上至下递归输出实例的数据
     */

    /**
     * 序列化中的继承问题:
     * 当一个父类实现序列化，子类自动实现序列化，不需要显式实现Serializable接口。
     * 一个子类实现了 Serializable 接口，它的父类都没有实现 Serializable 接口，要想将父类对象也序列化，
     * 就需要让父类也实现Serializable 接口。
     * 第二种情况中：如果父类不实现 Serializable接口的话，就需要有默认的无参的构造函数。这是因为一个 Java
     * 对象的构造必须先有父对象，才有子对象，反序列化也不例外。在反序列化时，为了构造父对象，只能调用父类的
     * 无参构造函数作为默认的父对象。因此当我们取父对象的变量值时，它的值是调用父类无参构造函数后的值。在这
     * 种情况下，在序列化时根据需要在父类无参构造函数中对变量进行初始化，否则的话，父类变量值都是默认声明的
     * 值，如 int 型的默认是 0，string 型的默认是 null。
     */
    public class People{
        private int num;

        public People(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "People{" +
                    "num=" + num +
                    '}';
        }
    }

    /**
     * 如果进行序列化和反序列化，num为0，name，age有值
     */
    public class Person extends People implements Serializable{
        /**
         * 如果是通过网络传输的话，如果Person类的serialVersionUID不一致，那么反序列化就不能正常进行。
         * 例如在客户端A中Person类的serialVersionUID=1L，而在客户端B中Person类的serialVersionUID=2L
         * 那么就不能重构这个Person对象
         */
        private static final long serialVersionUID = 1L;

        private String name;
        private int age;

        public Person(int num, String name, int age) {
            super(num);
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 测试序列化与反序列化
     */
    public void trySeritest(){
        File file = new File("file"+File.separator+"out.txt");

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            Person person = new Person(10,"zs",22);
            Log.d(TAG, "trySeritest: 序列化 ： " + person.toString());
            objectOutputStream.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Person person = (Person) objectInputStream.readObject();
            Log.d(TAG, "trySeritest: 反序列化： " + person.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 结果完全一样。如果我把Person类中的implements Serializable 去掉，Person类就不能序列化了。此时再运
         * 行上述程序，就会报java.io.NotSerializableException异常。
         */
    }

    /**
     * 静态变量序列化
     * 串行化只能保存对象的非静态成员交量，不能保存任何的成员方法和静态的成员变量，而且串行化保存的只是变量的值，
     * 对于变量的任何修饰符都不能保存。
     *如果把Person类中的name定义为static类型的话，试图重构，就不能得到原来的值，只能得到null。说明对静态成员
     * 变量值是不保存的。这其实比较容易理解，序列化保存的是对象的状态，静态变量属于类的状态，因此 序列化并不保存静态变量。
     */

    /**
     * transient关键字
     * 经常在实现了 Serializable接口的类中能看见transient关键字。这个关键字并不常见。 transient关键字的作用
     * 是：阻止实例中那些用此关键字声明的变量持久化；当对象被反序列化时（从源文件读取字节序列进行重构），这样的
     * 实例变量值不会被持久化和恢复。
     * 当某些变量不想被序列化，同是又不适合使用static关键字声明，那么此时就需要用transient关键字来声明该变量。
     */

    /**
     * 在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。
     * 注：对于某些类型的属性，其状态是瞬时的，这样的属性是无法保存其状态的。例如一个线程属性或需要访问IO、
     * 本地资源、网络资源等的属性，对于这些字段，我们必须用transient关键字标明，否则编译器将报措。
     */
    public class PersonA implements Serializable{
        private String name;
        transient int age;

        public PersonA(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "PersonA{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

    }

    /**
     * Android中Parcelable的原理和使用方法
     * 进行Android开发的时候，无法将对象的引用传给Activities或者Fragments，我们需要将这些对象放到一个Intent
     * 或者Bundle里面，然后再传递。简单来说就是将对象转换为可以传输的二进制流(二进制序列)的过程,这样我们就可以
     * 通过序列化,转化为可以在网络传输或者保存到本地的流(序列),从而进行传输数据 ,那反序列化就是从二进制流(序列)
     * 转化为对象的过程.
     *
     * Parcelable是Android为我们提供的序列化的接口,Parcelable相对于Serializable的使用相对复杂一些,但
     * Parcelable的效率相对Serializable也高很多,这一直是Google工程师引以为傲的,有时间的可以看一下Parcelable
     * 和Serializable的效率对比 Parcelable vs Serializable 号称快10倍的效率
     *
     * Parcelable接口的实现类是可以通过Parcel写入和恢复数据的,并且必须要有一个非空的静态变量 CREATOR,而且还给
     * 了一个例子,这样我们写起来就比较简单了
     *
     * Parcel的简介
     * 在介绍之前我们需要先了解Parcel是什么?Parcel翻译过来是打包的意思,其实就是包装了我们需要传输的数据,然后
     * 在Binder中传输,也就是用于跨进程传输数据
     *
     * 简单来说，Parcel提供了一套机制，可以将序列化之后的数据写入到一个共享内存中，其他进程通过Parcel可以从
     * 这块共享内存中读出字节流，并反序列化成对象,下图是这个过程的模型。hdpi下
     *
     * Parcel可以包含原始数据类型（用各种对应的方法写入，比如writeInt(),writeFloat()等），可以包含Parcelable
     * 对象，它还包含了一个活动的IBinder对象的引用，这个引用导致另一端接收到一个指向这个IBinder的代理IBinder。
     * Parcelable通过Parcel实现了read和write的方法,从而实现序列化和反序列化
     *
     * 根据Parcel的注释，我们了解了数据的去向：
     *
     * 有一个专门负责IBinder传输数据的容器。
     * （一个可以进程共享的内存区）
     * Parcel可以把数据压入，另一端的Parcel可以把数据取走
     * （通过实现我们可以推断出保存数据的是一个先进先出的堆栈）
     * Parcel仅仅是为了实现高性能的IPC通信，在其他的持久化方案在并不推荐。
     *
     * Parcel的write和read方法全是native方法：一般来说使用JNI的速度肯定是比使用Java方法要快，因为他绕过了Java
     * 层的api，不过这个并不是速度的保证，真正的优势是避免的大量的反射操作，减少了临时变量的创建，提高了序列化的效率
     *
     * Parcelable中的三大过程介绍(序列化,反序列化,描述)
     * 什么是序列化
     *
     * 序列化，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。
     *
     * 到这里,基本上关系都理清了,也明白简单的介绍和原理了,接下来在实现Parcelable之前,介绍下实现Parcelable的三大流程
     *
     * 实现Parcelable的作用
     *
     * 1）永久性保存对象，保存对象的字节序列到本地文件中；
     *
     * 2）通过序列化对象在网络中传递对象；
     *
     * 3）通过序列化在进程间传递对象。
     *
     * 首先写一个类实现Parcelable接口,会让我们实现两个方法:
     *
     * describeContents 描述
     * 其中describeContents就是负责文件描述.通过源码的描述可以看出,只针对一些特殊的需要描述信息的对象,需要返回
     * 1,其他情况返回0就可以
     * writeToParcel 序列化
     * 我们通过writeToParcel方法实现序列化,writeToParcel返回了Parcel,所以我们可以直接调用Parcel中的write
     * 方法,基本的write方法都有,对象和集合比较特殊下面单独讲,基本的数据类型除了boolean其他都有,Boolean可以使
     * 用int或byte存储
     *
     * 反序列化需要定义一个CREATOR的变量,上面也说了具体的做法,这里可以直接复制Android给的例子中的,也可以自己定
     * 义一个(名字千万不能改),通过匿名内部类实现Parcelable中的Creator的接口
     */
    public static class Book implements Parcelable{

        protected Book(Parcel in) {

        }

        public static final Creator<Book> CREATOR = new Creator<Book>() {
            @Override
            public Book createFromParcel(Parcel in) {
                return new Book(in);
            }

            @Override
            public Book[] newArray(int size) {
                return new Book[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    public static class ParcelTest implements Parcelable{
        private int count;
        private String name;
        private ArrayList<String> tags;
        private Book book;
        //如果是集合，一定要先初始化
        private ArrayList<Book> books = new ArrayList<>();

        /**
         * 反序列化
         * @param in
         */
        protected ParcelTest(Parcel in) {
            count = in.readInt();
            name = in.readString();
            tags = in.createStringArrayList();
            //读取对象需要提供一个类加载器去读取,因为写入的时候写入了类的相关信息
            book = in.readParcelable(Book.class.getClassLoader());
            //读取集合也分为两类,对应写入的两类

            //这一类需要用相应的类加载器去获取
            in.readList(books,Book.class.getClassLoader());

            //这一类需要使用类的CREATOR去获取
            in.readTypedList(books,Book.CREATOR);
//            books = in.createTypedArrayList(Book.CREATOR);
            /**
             * 写入和读取集合有两种方式,
             * 一种是写入类的相关信息,然后通过类加载器去读取, –> writeList | readList
             * 二是不用类相关信息,创建时传入相关类的CREATOR来创建 –> writeTypeList | readTypeList | createTypedArrayList
             * 第二种效率高一些
             * 一定要注意如果有集合定义的时候一定要初始化 like this –>
             * public ArrayList<T> demo = new ArrayList<>();
             */

        }

        /**
         * 负责反序列化
         */
        public static final Creator<ParcelTest> CREATOR = new Creator<ParcelTest>() {
            /**
             * 从序列化对象中，获取原始的对象
             * @param in
             * @return
             */
            @Override
            public ParcelTest createFromParcel(Parcel in) {
                return new ParcelTest(in);
            }

            /**
             * 创建指定长度的原始对象数组
             * @param size
             * @return
             */
            @Override
            public ParcelTest[] newArray(int size) {
                return new ParcelTest[size];
            }
        };

        /**
         * 描述
         * 返回的是内容的描述信息
         * 只针对一些特殊的需要描述信息的对象,需要返回1,其他情况返回0就可以
         * @return
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * 序列化
         * @param dest
         * @param flags
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(count);
            dest.writeString(name);
            //序列化一个String的集合
            dest.writeStringList(tags);
            // 序列化对象的时候传入要序列化的对象和一个flag,
            // 这里的flag几乎都是0,除非标识当前对象需要作为返回值返回,不能立即释放资源
            dest.writeParcelable(book,0);
            // 序列化一个对象的集合有两种方式,以下两种方式都可以

            //这些方法们把类的信息和数据都写入Parcel，以使将来能使用合适的类装载器重新构造类的实例.所以效率不高
            dest.writeList(books);

            /**
             * 这些方法不会写入类的信息，取而代之的是：读取时必须能知道数据属于哪个类并传入正确的Parcelable.Creator来创建对象
             * 而不是直接构造新对象。（更加高效的读写单个Parcelable对象的方法是：直接调用Parcelable.writeToParcel()和Parcelable.Creator.createFromParcel()）
             */
            dest.writeTypedList(books);
        }
    }

    /**
     * Parcelable和Serializable的区别和比较
     * Parcelable和Serializable都是实现序列化并且都可以用于Intent间传递数据,Serializable是Java的实现方式,
     * 可能会频繁的IO操作,所以消耗比较大,但是实现方式简单 Parcelable是Android提供的方式,效率比较高,但是实现
     * 起来复杂一些 , 二者的选取规则是:内存序列化上选择Parcelable, 存储到设备或者网络传输上选择Serializable
     * (当然Parcelable也可以但是稍显复杂)
     *
     * 选择序列化方法的原则
     *
     * 1）在使用内存的时候，Parcelable比Serializable性能高，所以推荐使用Parcelable。
     *
     * 2）Serializable在序列化的时候会产生大量的临时变量，从而引起频繁的GC。
     *
     * 3）Parcelable不能使用在要将数据存储在磁盘上的情况，因为Parcelable不能很好的保证数据的持续性在外界有变化
     * 的情况下。尽管Serializable效率低点，但此时还是建议使用Serializable 。
     */

    /**
     * 下面介绍下Parcelable和Serializable的作用、效率、区别及选择。
     *
     * 1、作用
     * Serializable的作用是为了保存对象的属性到本地文件、数据库、网络流、rmi以方便数据传输，当然这种传输可以是
     * 程序内的也可以是两个程序间的。而Android的Parcelable的设计初衷是因为Serializable效率过慢，为了在程序内
     * 不同组件间以及不同Android程序间(AIDL)高效的传输数据而设计，这些数据仅在内存中存在，Parcelable是通过
     * IBinder通信的消息的载体。
     *
     * 从上面的设计上我们就可以看出优劣了。
     *
     * 2、效率及选择
     * Parcelable的性能比Serializable好，在内存开销方面较小，所以在内存间数据传输时推荐使用Parcelable，如
     * activity间传输数据，而Serializable可将数据持久化方便保存，所以在需要保存或网络传输数据时选择
     * Serializable，因为android不同版本Parcelable可能不同，所以不推荐使用Parcelable进行数据持久化。
     *
     * 3、编程实现
     * 对于Serializable，类只需要实现Serializable接口，并提供一个序列化版本id(serialVersionUID)即可。
     * Parcelable则需要实现writeToParcel、describeContents函数以及静态的CREATOR变量，实际上就是将如
     * 何打包和解包的工作自己来定义(自定义选择属性序列化)，而序列化的这些操作完全由底层实现。
     *
     * 4、高级功能上
     * Serializable序列化不保存静态变量，可以使用Transient关键字对部分字段不进行序列化，也可以覆盖
     *
     * writeObject、readObject方法以实现序列化过程自定义。
     */

    /**
     * FileChannel
     * FileChannel类可以实现常用的read，write以及scatter/gather操作，同时它也提供了很多专用于文件的新方法。
     * 这些方法中的许多都是我们所熟悉的文件操作。
     *
     * 文件通道总是阻塞式的，因此不能被置于非阻塞模式。现代操作系统都有复杂的缓存和预取机制，使得本地磁盘I/O操作
     * 延迟很少。网络文件系统一般而言延迟会多些，不过却也因该优化而受益。面向流的I/O的非阻塞范例对于面向文件的操
     * 作并无多大意义，这是由文件I/O本质上的不同性质造成的。对于文件I/O，最强大之处在于异步I/O（asynchronous I/O）
     * ，它允许一个进程可以从操作系统请求一个或多个I/O操作而不必等待这些操作的完成。发起请求的进程之后会收到它请求
     * 的I/O操作已完成的通知。
     * FileChannel对象是线程安全（thread-safe）的。多个进程可以在同一个实例上并发调用方法而不会引起任何问题，
     * 不过并非所有的操作都是多线程的（multithreaded）。影响通道位置或者影响文件大小的操作都是单线程的（single-threaded）。
     * 如果有一个线程已经在执行会影响通道位置或文件大小的操作，那么其他尝试进行此类操作之一的线程必须等待。并发行
     * 为也会受到底层的操作系统或文件系统影响。
     * 每个FileChannel对象都同一个文件描述符（file descriptor）有一对一的关系，所以上面列出的API方法与在您最
     * 喜欢的POSIX（可移植操作系统接口）兼容的操作系统上的常用文件I/O系统调用紧密对应也就不足为怪了。本质上讲，
     * RandomAccessFile类提供的是同样的抽象内容。在通道出现之前，底层的文件操作都是通过RandomAccessFile类的
     * 方法来实现的。FileChannel模拟同样的I/O服务，因此它的API自然也是很相似的。
     *
     * 三者之间的方法对比：
     * FILECHANNEL	RANDOMACCESSFILE	POSIX SYSTEM CALL
     * read( )	read( )	read( )
     * write( )	write( )	write( )
     * size( )	length( )	fstat( )
     * position( )	getFilePointer( )	lseek( )
     * position (long newPosition)	seek( )	lseek( )
     * truncate( )	setLength( )	ftruncate( )
     * force( )	getFD().sync( )	fsync( )
     *
     * 1、打开FileChannel
     * 在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，需要通过使用一个
     * InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例。下面是通过RandomAccessFile
     * 打开FileChannel的示例：
     *
     * RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
     * FileChannel inChannel = aFile.getChannel();
     * 2、从FileChannel读取数据
     * 调用多个read()方法之一从FileChannel中读取数据。如：
     *
     * ByteBuffer buf = ByteBuffer.allocate(48);
     * int bytesRead = inChannel.read(buf);
     * 首先，分配一个Buffer。从FileChannel中读取的数据将被读到Buffer中。
     *
     * 然后，调用FileChannel.read()方法。该方法将数据从FileChannel读取到Buffer中。read()方法返回的int值表
     * 示了有多少字节被读到了Buffer中。如果返回-1，表示到了文件末尾。
     */
    public void fileChannelTest(){
        //注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt", "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer allocate = ByteBuffer.allocate(48);
            int bytesRead = channel.read(allocate);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                allocate.flip();
                while (allocate.hasRemaining()) {
                    System.out.print((char) allocate.get());
                }

                allocate.clear();
                bytesRead = channel.read(allocate);
            }
            System.out.println("wan");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、向FileChannel写数据
     * 使用FileChannel.write()方法向FileChannel写数据，该方法的参数是一个Buffer。如：
     *
     * 注意FileChannel.write()是在while循环中调用的。因为无法保证write()方法一次能向FileChannel写入多少字节，
     * 因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
     */
    public void tryWriterFileChannel(FileChannel channel){
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);//48个字节
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            try {
                channel.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 5、FileChannel的position方法
     * 有时可能需要在FileChannel的某个特定位置进行数据的读/写操作。可以通过调用position()方法获取FileChannel的当前位置。
     *
     * 也可以通过调用position(long pos)方法设置FileChannel的当前位置。
     *
     * 这里有两个例子:
     *
     * long pos = channel.position();
     * channel.position(pos +123);
     * 如果将位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。
     *
     * 如果将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并写入数据。这可能导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。
     *
     * 6、FileChannel的size方法
     * FileChannel实例的size()方法将返回该实例所关联文件的大小。如:
     *
     * long fileSize = channel.size();
     * 7、FileChannel的truncate方法
     * 可以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除。如：
     *
     * channel.truncate(1024);
     * 这个例子截取文件的前1024个字节。
     *
     * 8、FileChannel的force方法
     * FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上。出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。要保证这一点，需要调用force()方法。
     *
     * force()方法有一个boolean类型的参数，指明是否同时将文件元数据（权限信息等）写到磁盘上。
     *
     * 下面的例子同时将文件数据和元数据强制写到磁盘上：
     */

    /**
     * 内存映射
     * 内存映射文件的作用是使一个磁盘文件与存储空间中的一个缓冲区建立映射关系，然后当从缓冲区中取数据，就相当于
     * 读文件中的相应字节；而将数据存入缓冲区，就相当于写文件中的相应字节。这样就可以不使用read和write直接执行I/O了。
     * 通过操作内存来操作文件，省了从磁盘文件拷贝数据到用户缓冲区的步骤，所以十分高效。
     */

    /**
     *FileChannel的map:在FileChannel中，也定义了一个内存映射的操作，我们可以使用它来加速文件的读写。
     * map方法
     * FileChannel提供了map方法来把文件影射为内存映像文件：
     *
     * MappedByteBuffer map(int mode,long position,long size);
     * 可以把文件的从position开始的size大小的区域映射为内存映像文件，mode指出了 可访问该内存映像文件的方式：
     * READ_ONLY，READ_WRITE，PRIVATE。
     *
     * READ_ONLY,（只读）： 试图修改得到的缓冲区将导致抛出 ReadOnlyBufferException.(MapMode.READ_ONLY)
     * READ_WRITE（读/写）： 对得到的缓冲区的更改最终将传播到文件；该更改对映射到同一文件的其他程序不一定是可见的。
     * (MapMode.READ_WRITE)
     * PRIVATE（专用）： 对得到的缓冲区的更改不会传播到文件，并且该更改对映射到同一文件的其他程序也不是可见的；
     * 相反，会创建缓冲区已修改部分的专用副本。 (MapMode.PRIVATE)
     */
    public void tryFileChannelMap(){
        FileChannel channel = null;
        FileChannel wordFileChannel = null;
        try (RandomAccessFile file = new RandomAccessFile("a.txt", "rw")) {
            channel = file.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 20);

            try (RandomAccessFile wordFile = new RandomAccessFile("b.txt", "rw")) {
                wordFileChannel = wordFile.getChannel();
                MappedByteBuffer mappedByteBuffer = wordFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
                mappedByteBuffer.put(map);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
                wordFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用MappedByteBuffer实现内存共享
     * 实际上，我们可以在两个Java进程中各使用一次map将文件映射到内存，这样两个进程就可以直接通过这个共享内存来
     * 实现进程间的数据通信了。
     */
    public void tryFileChannelMapShare(){
        //写入
        new Thread(() -> {
            FileChannel fileChannel = null;
            try (RandomAccessFile file = new RandomAccessFile("a.txt", "rw")) {
                fileChannel = file.getChannel();
                MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
                map.put("how are you".getBytes());
                Thread.sleep(10000);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void tryFileChannelMapShare2(){
        //读取
        FileChannel fileChannel = null;
        try (RandomAccessFile accessFile = new RandomAccessFile("a.txt", "rw")) {
            fileChannel = accessFile.getChannel();
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 2, 20);
            while (map.hasRemaining()){
                Log.d(TAG, "tryFileChannelMapShare2: the date is : " + map.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 原理
     * 在sun.nio.ch.FileChannelImpl里有map的具体实现：
     *
     *             try {
     *                 // If no exception was thrown from map0, the address is valid
     *                 addr = map0(imode, mapPosition, mapSize);
     *             } catch (OutOfMemoryError x) {
     *
     *
     *     private native long map0(int prot, long position, long length)
     * 调用的是一个native方法，这个native方法的实现位于solaris/native/sun/nio/ch/FileChannelImpl.c
     *
     * 是这样的
     *
     * JNIEXPORT jlong JNICALL
     * Java_sun_nio_ch_FileChannelImpl_map0(JNIEnv *env, jobject this,
     *                                      jint prot, jlong off, jlong len)
     * {
     *     void *mapAddress = 0;
     *     jobject fdo = (*env)->GetObjectField(env, this, chan_fd);
     *     jint fd = fdval(env, fdo);
     *     int protections = 0;
     *     int flags = 0;
     *
     *     if (prot == sun_nio_ch_FileChannelImpl_MAP_RO) {
     *         protections = PROT_READ;
     *         flags = MAP_SHARED;
     *     } else if (prot == sun_nio_ch_FileChannelImpl_MAP_RW) {
     *         protections = PROT_WRITE | PROT_READ;
     *         flags = MAP_SHARED;
     *     } else if (prot == sun_nio_ch_FileChannelImpl_MAP_PV) {
     *         protections =  PROT_WRITE | PROT_READ;
     *         flags = MAP_PRIVATE;
     *     }
     *
     *     mapAddress = mmap64(
     *         0,                    /* Let OS decide location /
     *len,                  /* Number of bytes to map /
            *protections,          /* File permissions /
            *flags,                /* Changes are shared /
            *fd,                   /* File descriptor of mapped file /
            *off);                 /* Offset into file /
     *
             *if(mapAddress ==MAP_FAILED)

    {
     *if (errno == ENOMEM) {
     *JNU_ThrowOutOfMemoryError(env, "Map failed");
     *return IOS_THROWN;
     *}
     *return handle(env, -1, "Map failed");
     *}
     *
             *return((jlong)(unsigned long)mapAddress);
     *
}
     *哈哈，原来还是使用的mmap这个系统调用。所以，Java中的很多操作其实就是linux系统调用封了一层皮而已。
     */

    /**
     * 要注意的地方
     * 我们在讲解DirectBuffer的时候，就跳过了一个重要的地方，那就是它是怎么回收的。这个机制十分复杂，牵扯到GC的
     * 具体实现，同样的问题，在MappedByteBuffer中也存在。
     *
     * 如果你使用了FileChannel.map方法去映射一个文件，然后马上关闭这个channel，然后再试图删除文件，就会发现不
     * 能成功。这是因为MappedByteBuffer还没有被回收，文件句柄还没有释放。而具体什么时候才会释放，以及能不能提
     * 前释放。这个问题等我们讲完了GC之后再来看。现在只需要知道基本的用法就好了。
     */

    public void trySecondFileChannelRead(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharBuffer charBuffer = null;
            /**
             * Path 既可以表示一个目录，也可以表示一个文件，就像 File 那样——当然了，Path 是用来取代 File 的。
             * xxxx为文件路径
             */
            Path path = Paths.get("xxxx");
            //从文件中获取一个 channel（通道，对磁盘文件的一种抽象）。
            try (FileChannel open = FileChannel.open(path)) {
                //把文件映射到内存缓冲区
                MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0, open.size());
                //打印数据
                if(map!=null){
                    //由于 decode() 方法的参数是 MappedByteBuffer，这就意味着我们是从内存中而不是磁盘中读入的文件内容，所以速度会非常快。
                    charBuffer = Charset.forName("UTF-8").decode(map);
                }
                Log.d(TAG, "trySecondFileChannelRead: the date is : " + charBuffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void trySecondFileChannelWrite(){
        //写入数据
        CharBuffer charBuffer = CharBuffer.wrap("adasxasdasd");
        //创建存放路径
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Path path = Paths.get("xxxx");
            /**
             * 创建文件通道
             * 仍然使用的 open 方法，不过增加了 3 个参数，前 2 个很好理解，表示文件可读（READ）、可写（WRITE）；
             * 第 3 个参数 TRUNCATE_EXISTING 的意思是如果文件已经存在，并且文件已经打开将要进行 WRITE 操作，则其长度被截断为 0。
             */
            try (FileChannel open = FileChannel.open(path, StandardOpenOption.WRITE,
                    StandardOpenOption.READ, StandardOpenOption.TRUNCATE_EXISTING)) {
                //指定文件大小为 1024，即 1KB 的大小
                MappedByteBuffer map = open.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
                if(map!=null){
                    map.put(Charset.forName("UTF-8").encode(charBuffer));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 03、MappedByteBuffer 的遗憾
     * 据说，在 Java 中使用 MappedByteBuffer 是一件非常麻烦并且痛苦的事，主要表现有：
     *
     * 1）一次 map 的大小最好限制在 1.5G 左右，重复 map 会增加虚拟内存回收和重新分配的压力。也就是说，如果文件大小不确定的话，就不太友好。
     *
     * 2）虚拟内存由操作系统来决定什么时候刷新到磁盘，这个时间不太容易被程序控制。
     *
     * 3）MappedByteBuffer 的回收方式比较诡异。
     *
     * 再次强调，这三种说法都是据说，我暂时能力有限，也不能确定这种说法的准确性，很遗憾。
     *
     * 04、比较文件操作的处理时间
     * 嗨，朋友，阅读完以上的内容之后，我想你一定对内存映射文件有了大致的了解。但我相信，如果你是一名负责任的程序员，你一定还想知道：内存映射文件的读取速度究竟有多快。
     *
     * 为了得出结论，我叫了另外三名竞赛的选手：InputStream（普通输入流）、BufferedInputStream（带缓冲的输入流）、RandomAccessFile（随机访问文件）。
     *
     * 方法	时间
     * 普通输入流	龟速，没有耐心等出结果
     * 随机访问文件	龟速，没有耐心等下去
     * 带缓冲的输入流	29966
     * 内存映射文件	914
     */

}
