#!/usr/bin/env bash

mainFun(){
    echo "所有的选项均不需要输入 "[]" 输入数字即可操作"
    echo "是否更新代码"
    echo "[1] 更新代码"
    echo "[2] 使用本地代码"
    read updateEnable

    if [[ ${updateEnable} ==1 ]]; then
        updateCode
    elif [[ ${updateEnable}==2 ]]; then
        buildProject
    else
        echo "输入错误，请重新输入"
    fi
}


updateCode(){
     echo "请选择您的团队使用的版本控制工具"
     echo "[1] svn"
     echo "[2] git"
     read toolType
     if [[ ${toolType}==1 ]];then
        svn update
        updateResult
     elif [[ ${toolType}==2 ]];then
        git pull
        updateResult
     else
        echo "输入错误，请重新输入"
        updateCode
     fi

}

buildProject(){
    echo "准备构建项目"
    # gradlew文件增加可执行权限
    chmod u+x gradlew.sh
    switchProject
}

switchProject(){
    echo "请选择您要构建的项目"
    echo "[1] MBA大师"
    echo "[2] Finance(金融)"
    echo "[3] Art(艺考)"
    read -p project
    if [[ ${project} -le 3 ]]; then
        java java SwitchProject ${project}
        echo "选择项目完成"
        switchServer
    else
        echo "输入错误，请重新输入"
        switchProject
    fi

}

switchServer(){
    echo "请选择您要打包的环境"
    echo "[1] 正式服务器"
    echo "[2] 测试服务器"
    read environment
    if [[ ${environment} -le 2 ]]; then
        echo "正在切换服务器"
        java SwitchServer ${project} ${environment}
        sleep 1
        echo "切换服务器完成，正在应用配置...."
        source ./gradlew generateDebugSources
        sleep 1
        assembleRelease
    else
        echo "输入错误，请重新输入"
        switchServer
    fi
}

updateResult(){
    echo "正在更新代码..."
    resultCode = $?
    if [[ ${resultCode} != 0 ]]; then
        echo "Error: 拉取代码失败"
        exit 1
    else
        echo "更新代码成功"
        buildProject
    fi
}

assembleRelease(){
    echo "开始打包"
    source ./gradlew assembleRelease
}

mainFun