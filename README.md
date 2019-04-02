## What's this

An gradle plugin  for android project  aspject

Support for multiple  library, Kotlin, Java

Support for  debug,release

Support for multiple flavors

## How to use

project:  build.gradle   

     dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath 'org.aspectj:aspectjtools:1.8.9'
       classpath 'com.dhaspject:dhaspject:1.0.0'
    }
    



app module(main module): build.gradle

    apply plugin: "com.dhaspject"
    laop{
    kotlinfiles("testpro.com","com.kotlin")  //kotlin package need to be aspject,not define will be all
    javafiles("com.lin")  //java package need to be aspject
    aopModule("api_log") // the aop libray name 
    aopType("debug")  // aop type, debug: debug mode, default: always use, close: unuse
    moduleType(1)  //App module need to be 1,  other library need to be 2, 
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
    moduleType(2)    //2 is important
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
    moduleType(2) //must to be 2
}

`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`

### [Dhaspject Demo](https://github.com/dikeboy/DhaspjectDemo)
