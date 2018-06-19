package com.example.dly.app_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by a01 on 2018/6/19.
 */

public class AnnotatedClass {

    //类
    public TypeElement mClassElement;

    //类内的注解变量
    public List<BindViewField> mFiled;

    //元素帮助类
    public Elements mElementUtils;

    public AnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
        this.mFiled = new ArrayList<>();
    }

    //添加注解变量
    public void addField(BindViewField field) {
        mFiled.add(field);
    }

    //获取包名
    public String getPackageName(TypeElement type) {
        return mElementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    //获取类名
    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    public JavaFile generateFinder() {

        //构建 inject 方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(Utils.FINDER, "finder");

        //inject函数内的核心逻辑,
        // host.btn1=(Button)finder.findView(source,2131427450);  ----生成代码
        // host.$N=($T)finder.findView(source,$L)                 ----原始代码
        // 对比就会发现这里执行了实际的findViewById绑定事件
        for (BindViewField field : mFiled) {
            methodBuilder.addStatement("host.$N=($T)finder.findView(source,$L)", field.getFieldName()
                    , ClassName.get(field.getFieldType()), field.getResId());
        }

        String packageName = getPackageName(mClassElement);
        String className = getClassName(mClassElement, packageName);
        ClassName bindClassName = ClassName.get(packageName, className);

        //构建类对象
        TypeSpec finderClass = TypeSpec.classBuilder(bindClassName.simpleName() + "$$Injector")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(Utils.INJECTOR, TypeName.get(mClassElement.asType())))   //继承接口
                .addMethod(methodBuilder.build())
                .build();

        return JavaFile.builder(packageName, finderClass).build();
    }

}
