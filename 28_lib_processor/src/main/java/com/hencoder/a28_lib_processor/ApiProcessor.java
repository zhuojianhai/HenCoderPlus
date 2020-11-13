package com.hencoder.a28_lib_processor;

import com.hencoder.a28_lib_annotations.ApiAnnotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 *
 */
public class ApiProcessor extends AbstractProcessor {
    Filer filer;
    Messager messager;

    //类名的前缀、后缀
    public static final String SUFFIX = "AutoGenerate";
    public static final String PREFIX = "My_";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {

//        for (Element element:roundEnvironment.getRootElements()){
//            messager.printMessage(Diagnostic.Kind.WARNING,"processor--"+element.getSimpleName());
//        }
        for (TypeElement typeElement : annotations) {
            for (Element e : env.getElementsAnnotatedWith(typeElement)) {
                //打印
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.toString());
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.getSimpleName());
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.getEnclosedElements().toString());

                //获取注解
                ApiAnnotation annotation = e.getAnnotation(ApiAnnotation.class);
                //获取元素名并将其首字母大写
                String name = e.getSimpleName().toString();
                char c = Character.toUpperCase(name.charAt(0));
                name = String.valueOf(c + name.substring(1));
                //包裹注解元素的元素, 也就是其父元素, 比如注解了成员变量或者成员函数, 其上层就是该类
                Element enclosingElement = e.getEnclosingElement();
                //获取父元素的全类名,用来生成报名
                String enclosingQualifiedname;
                if (enclosingElement instanceof PackageElement) {
                    enclosingQualifiedname = ((PackageElement) enclosingElement).getQualifiedName().toString();
                } else {
                    enclosingQualifiedname = ((TypeElement) enclosingElement).getQualifiedName().toString();
                }
                try {
                    //生成包名
                    String generatePackageName = enclosingQualifiedname.substring(0, enclosingQualifiedname.lastIndexOf("."));
                    // 生成的类名
                    String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;
                    //创建Java 文件
                    JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
                    // 在控制台输出文件路径
                    messager.printMessage(Diagnostic.Kind.WARNING, "Printing: " + f.toUri());
                    Writer w = f.openWriter();
                    try {
                        PrintWriter pw = new PrintWriter(w);
                        pw.println("package " + generatePackageName + ";");
                        pw.println("\npublic class " + genarateClassName + " { ");
                        pw.println("\n    /** print value */");
                        pw.println("    public static void print" + name + "() {");
                        pw.println("        // annotation parent elemnt: " + enclosingElement.toString());
                        pw.println("        System.out.println(\"code pathe : " + f.toUri() + "\");");
                        pw.println("        System.out.println(\"annotation element: " + e.toString() + "\");");
                        pw.println("        System.out.println(\"annotation version: " + annotation.version() + "\");");
                        pw.println("        System.out.println(\"annotation author: " + annotation.author() + "\");");
                        pw.println("        System.out.println(\"annotation date: " + annotation.date() + "\");");

                        pw.println("    }");
                        pw.println("}");
                        pw.flush();
                    } finally {
                        w.close();
                    }
                } catch (Exception e1) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            e1.toString());
                }
            }
        }

        return true;
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ApiAnnotation.class.getCanonicalName());
    }
}
