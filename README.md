## What's this

An gradle plugin  for android project  aspectj

Support for multiple  library, Kotlin, Java

Support for  debug mode,release mode or close

Support for multiple flavors

## If use kotlin  make sure  the @Aspect annotion class file use kotlin

## How to use

project:  build.gradle   

     dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath 'org.aspectj:aspectjtools:1.8.9'
       classpath 'com.dhaspject:dhaspject:1.1.1'
    }
    

## javafiles and kotlinfiles all empty will compile  all the file of current module,one of this empty will comile only select

app module(main module): build.gradle

    apply plugin: "com.dhaspject"
    laop{
    kotlinfiles("testpro.com","com.kotlin")  //kotlin package need to be aspject
    javafiles("com.lin")  //java package need to be aspject
    aopModule("api_log") // the aop library name 
    aopType("debug")  // aop type, debug: debug mode, default: always use, close: unuse
    hasFlavors(true) //if have flavors
}

`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`




api_log( aop module): build.gradle( the aop module, you can modify the name by yourself)

    apply plugin: "com.dhaspject"
    laop{
     aopModule("api_log")
    aopType("debug")
    useJavaTask(true) //(Opertion) IF kotlin file not effective
}


`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`


other module need aspject:
my_lib: build.gradle

    apply plugin: "com.dhaspject"
    laop{
    javafiles("com.lance.aspectlib") // witch package need to be aspject
    aopModule("api_log") //the aop modle
    aopType("debug")
}

`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`

### 参考
* [Dhaspject Demo](https://pub.dev/packages/undo_textfield)
* [android 无痕打点](https://github.com/dikeboy/IntrusiveClick)
* [AspectJ官网](https://eclipse.org/aspectj/)
