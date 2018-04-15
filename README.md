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
