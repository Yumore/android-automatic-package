@echo off
set DEFAULT_DIR=.
set GRADLE_FILE=build.gradle
set LOG_FILE=sync_clog.log

@rem set gradle to path once
set GRADLE_HOME=%DEFAULT_DIR%/gradle/gradle-file/bin
echo gradle home is %GRADLE_HOME%

@rem set gradle to system path ever
:: wmic ENVIRONMENT where "name='path' and username='<system>'" set VariableValue="%path%;e:\tools"

@rem set local code path to save
:: set svnpath=""
:: set gitpath=""
:start
@rem please select one of options
echo.
echo ************************************************
echo * Notice: all of options are excluded '[]'
echo ************************************************
echo.
echo [1] for updating the lasted code
echo [2] for using local code
set /p var="please select one of options: "
if %var% equ "" exit
if %var% == 1 goto update
if %var% == 2 goto build
@rem error option
echo Error: unsupported option
goto start


:update
echo.
echo please select git or svn to update your code version
echo [1] for using svn
echo [2] for using git
set /p type="please select one of options: "
if %type% equ "" exit
if %type% == 1 goto svn
if %type% == 2 goto git
@rem error option
echo Error: unsupported option
goto update


:svn
@rem using git to update code
:: cd %svnpath%
echo prepare to update...
svn update
echo update successful.
goto build

:git
@rem using git to update code
:: cd %gitpath%
echo prepare to update...
git pull
echo update successful.
goto build

:build
@rem select project
echo.
echo please select which project you wanna build
echo [1] build MBA
echo [2] build Finance
echo [3] build Art
set /p project="please select one of options: "
if !project! leq 3 (
    if %project% equ "" (
        exit
    ) else (
        java SwitchProject %project%
        echo prepare to build...
        goto package
    )
)
echo Error: unsupported option
goto build

:package
@rem prepare build project
echo.
echo please select which type do you wanna build.
echo [1] debug which type using test server only one file.
echo [2] beta which type using formal server only one file.
:: echo [3] release which type using formal server multiply channel.
echo.
echo *******************************************************************
echo * Notice: the third type will spend too much time during building
echo * DO NOT INTERRUPT DURING BUILDING
echo *******************************************************************
echo.
@rem read build type
set /p server="please input the type: "
if !server! leq 2 (
    if %server% equ "" (
        exit
    ) else (
        java SwitchServer %project% %server%
        sleep 1
        echo sync now, wait for moment...
        ::call gradlew build>>%LOG_FILE%
        call gradlew generateDebugSources
        echo sync finished, prepare to package...
        goto assemble
    )
)
@rem error option
echo unsupported option
goto package


:assemble
echo building now, wait for moment...
call gradlew assembleRelease
echo.
echo if error arise, please communicate wih developers, thanks for a lot.
echo.
pause
