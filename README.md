# dhaspject
android  aspectj aop, support for kotlin, java, Multiple libraries ， Multiple flavor, config by  debug mode




## How to use

project:  build.gradle   

     dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
       classpath 'com.dhaspject:dhaspject:1.0.0'
    }
    maybe should add `        maven { url "https://dl.bintray.com/dikeboy/dhaspject/" }`
app: build.gradle

    apply plugin: "com.dhaspject"
    laop{
    kotlinfiles("testpro.com","com.kotlin")  //kotlin package need to be aspject,not define will be all
    javafiles("com.lin")  //java package need to be aspject
    aopModule("api_log") // the aop libray name 
    aopType("debug")  // aop type, debug: debug mode, default: always use, close: unuse
    moduleType(1)  //App module need to be 1,  other library need to be 2, 
}
api_log( the aop module, you can define by yourself)

    apply plugin: "com.dhaspject"
    laop{
    aopType("debug")
    moduleType(2)    //2 is important
}
other module need aspject:
my_lib

    apply plugin: "com.dhaspject"
    laop{
    javafiles("com.lance.aspectlib")
    aopModule("api_log")
    aopType("debug")
    moduleType(2)
}
