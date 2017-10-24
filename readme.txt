使用说明：

    1. 安装jdk或者openjava，并配置环境变量

    2. 方式1：下载gradle并解压，配置Gradle_Home(可以不配置)，将 /gradle.x/bin 添加到path环境变量，其中gradle.x是你下载的版本名称
            注意：gradle版本必须在3.4+
       方式2：将下载的gradle解压，将gradle-x.y(x.y为版本号)重命名为“gradle-file”放到gradle目录下

    3. 下载Android SDK 版本在api 15+，
        并将/android-sdk/platform-tools和/android-sdk/tools添加到path环境变量
        注意：android-sdk下必须包含
        /android-sdk
            /build-tools/25.0.2
            /platform/android-25
            /platform-tools
            /tools
            /licenses
        其他可以自行选择

    4. 找到项目的local.properties
        1) 在Windows环境下，找到你Android SDK的目录，最好不要用中问和空格，可以有-和_，将sdk.dir=填入你SDK所在的目录。
            例如我的目录为E盘下的AndroidSDK，则填写如下：sdk.dir=E\:\\AndroidSDK
        2) 在Mac/Unix/Linux下和Windows类似。
            例如我在CentOS下SDK所在的目录为/data/android-sdk，则填写如下：sdk.dir=/data/android-sdk

    5. 找到项目所在目录的path.properties
        1)在Windows环境下，创建一个文件夹用来保存apk文件的目录，并将该目录所在的路径复制填写到apkDirectory=后面。
            例如我在Windows下所建的目录为E盘下的apkPath，则填写如下：apkDirectory=E\:\\apkPath
        2)在Mac/Unix/Linux环境下，和Windows环境类似。
            例如我在CentOS下创建的目录在usr目录下的apkPath，而我的用户名为Nathan，则填写如下：apkDirectory=/usr/Nathan/apkPath

    6. 如何开始打包
        1)在Windows下 双击build.bat或者在cmd中运行 build.bat 按照提示操作即可
        2)在Mac/Unix/Linux环境下需要先在Terminal（终端）执行"chmod u+x build.sh"(不包含"")，授予build.sh的可执行权限
            注意：执行 chmod a+x build.sh 也可以授予build.sh的可执行权限
            然后按照提示操作即可