# 自学报告
## vi编辑器的使用
### vi编辑器主要分为三种模式：
> * 命令行模式
> * 插入模式
> * 底行模式

### 要使用vi编辑器，例如用vi打开本次作业的helloworld.java，可以直接在linux命令行中输入命令  

```
vi helloworld.java
```

### 这样就可以进入vi的命令行模式，接下来要想进入插入模式，直接在键盘上按i，若是想进入底行模式，则要按：（注意这个冒号不单只冒号键，因为冒号和分号是同一个建，按这个键默认是分号，因此实际上要按shift+；才能输入冒号：），同时也要注意插入模式和底行模式不能直接转换，要先按esc退回到命令行模式，再按相应的键才能进入。
### 接下来简单的说一下每一个模式
> * 插入模式：和普通文本编辑器一样，可以直接键盘输入字符到文本，也可以按回退键backspace删除字符（实训的pdf说不能删除字符，感觉有误）。

> * 命令行模式：该模式下是不能直接用键盘输入字符到文本的，但还是可以输入命令来修改文本，倒霉的是输入的命令时看不见的例如输入 6yw 进行复制，输入了6后看不见已经输入了6，因此体验不是很好，当然输入完整个命令后命令行左下角会有提示复制了什么。
>   * u 撤销命令 
>   * yw 复制一个单词（从光标开始）到缓冲区
>   * yy 复制一行（光标所在行）到缓冲区
>   * #yw #代表输入命令时的数字，复制#个字到缓冲区
>   * #yy 复制#行到缓冲区
>   * p 粘贴
>   * x 删除光标所在位置的一个字符
>   * #x #代表输入命令时的数字，删除光标所在位置往后#个字符

> * 底行模式：该模式下的命令就可以在左下角看到了,输入完命令后按回车即可执行。
>   * set nu 列出行号
>   * #,#代表数字，直接输入一个数字并回车执行，直接跳转到该行。
>   * /#,#代表要搜索的字符，输入/再输入要搜索的字符，可以查找关键字，若第一次查找的不是需要的，按n继续找。
>   * ?#,与上一个命令 /# 功能相同
>   * q 退出vi编辑器
>   * qw 退出并保存。
~~（感觉还是sublime更好→_→）~~
## Java学习报告
### java与c最大的不同就是java是完全面向对象的语言，因此它没有一个单独的main函数，而是在类中有一个静态的main函数，不过除此之外，java很多语法什么的都和c很相似，什么String，int，double类型都是差不多的，也没了指针，因此学起来并不算太难。不过这次主要学的是java的Gui，感觉还是很简陋的，位置布局什么的相比于html css的web应用，还有C#的uwp应用来讲都很弱。
### 对于java的GUI，主要学习了这些

```
JFrame frame = new JFrame("Easy Calculator");
frame.setBounds(framePositionOfX, framePositionOfY, frameWidth, frameHeight);
```
这三行代码第一行先新建一个窗口gui，第二行设置窗口出现的位置和大小，第三行将它显示出来

```
private JTextField firstNum = new JTextField("1" );
frame.add(firstNum);
```
第一行代码新建一个文本区域，第二行代码将它添加到了窗口中，除了JTextField外，还有很多样式，例如JButton，JLabel等等，按需查找资料了解它们，然后加入即可。

这些组件都有一些方法，在设置按钮点击函数的时候采用了一些简单的，例如setText（），getText（）等等。
下面就是添加点击函数的方法，先定义一个监听器类（继承ActionListener）,这个类主要要实现一个actionPerformed的函数，这个函数就是按钮点击后执行的函数，其中语法开始也没怎么查，基本按照c++的写法来写，几乎没有什么不同。当然，不同点还是有的，例如实现这个函数接口的话前面要加一个@Override，而c++根本不需要加，还有就是String类居然没有重载中括号，只能用其中的方法charAt（），让我很是不习惯。
```
private class EqualOperation implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		double firstNumber = Double.parseDouble(firstNum.getText() ) ;
		double secondNumer = Double.parseDouble(secondNum.getText() ) ;
		double result = 0.0 ;
		switch(signArea.getText().charAt(0)){
		case '+': 
			result = firstNumber + secondNumer ;  
			break;
		case '-': 
			result = firstNumber - secondNumer ; 
			break;
		case '*': 
			result = firstNumber * secondNumer ;  
			break;
		case '/': 
			result = firstNumber / secondNumer ;  
			break;
		default: 				    					break;							
		}
		answerArea.setText(String.valueOf(result));
	}                   
};
```
------------
写完这个类之后，要想添加到按钮中，就只要写这行代码，就可以实现对按钮的点击监听
```
buttonAdd.addActionListener(new equalOperation() );
```
掌握了这些，写一个简单计算器就不是什么难事了。


## Ant的使用
### Ant感觉像是大一实训时用的makefile,编写一个build.xml,例如下面例子，选自教程
```
<?xml version="1.0"?>
<project name="helloWorld">
    <target name="sayHelloWorld">
        <echo message="Hello">
    </target>
</project>
```
### 命令行输入ant sayHelloWorld既可看到命令行输出了Hello
> * project元素：至少有一个，否则会报错
>   * name元素：project的名字。
>   * default： 默认执行的tarject
>   target元素：代表要执行的任务
>   * name元素: target的名字
>   * depends: 描述依赖关系

### 以下是计算器的build代码
```
<?xml version="1.0"?>
<project name="helloworld" default="run">
	<target name="clean">
		<delete dir="build"/>
	</target>
	<target name="compile" depends="clean">
		<mkdir dir="build/classes"/>
	<javac srcdir="src" destdir="build/classes"/>
	</target>

	<target name="run" depends="compile">
		<java classname="Calculator" fork="true">
			<classpath>
				<pathelement path="build/classes"/>
			</classpath>
		</java>
	</target>
</project>

```
## Junit的使用
@Test：测试方法

@Before：使用了该元数据的方法在每个测试方法执行之前要执行一次。

@After：使用了该元数据的方法在每个测试方法之后要执行一次
### 在两个文本中分别写两个类。
```
import java.util.*;
public class HelloWorld{
	String str;
	public void hello(){
		str = "Hello World!";
	}

	public String getStr(){
		return str;
	}
}
```
------------------------------------------------------------------------------------------
```
import static org.junit.Assert.*;
import org.junit.Test;

public class HelloWorldTest{
	public HelloWorld helloworld = new HelloWorld();
	@Test
	public void testHello(){
		helloworld.hello();
		assertEquals("Hello World!" , helloworld.getStr());
	}
}
```
### 写完这两个类（同一文件夹）之后，然后把junit-4.9.jar也放到同一目录，在命令行输入这两行命令。
```
javac -classpath .:junit-4.9.jar HelloWorldTest.java 
java -classpath .:junit-4.9.jar -ea org.junit.runner.JUnitCore  HelloWorldTest 
```

## sonor学习
### 新建一个文本sonar-project.properties，按照教程写了下列的代码，其中projectKey，projectName，java-module.sonar.projectBaseDir改为calculator。
```
#required metadata
sonar.projectKey=calculator
sonar.projectName=calculator
sonar.projectVersion=1.0
sonar.sourceEncoding=UTF-8
sonar.modules=java-module

java-module.sonar.projectName=Java Module
java-module.sonar.language=java 

java-module.sonar.sources=.
java-module.sonar.projectBaseDir=calculator
```
### 写完后将该文本与calculator文件夹放在同一目录下，然后控制台进入到该目录，运行sonar-runner，然后到localhost:9000查看即可看到代码的质量等等。
