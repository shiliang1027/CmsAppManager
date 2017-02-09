#!/bin/sh
# 下面参数请根据主机环境配置
# ---------------------------------------
#
# ##### 必填项 #####
#
# APP_SN         进程唯一标识,同一台机器上运行的多个进程名称不能相同
#
# JAVA_OPTS      JAVA启动参数
#
# MAIN_CLASS     程序的入口类
#
# ##### 选填项 #####
#
# LANG           语言版本(默认使用环境变量配置)
#
# JAVA_HOME      JDK安装目录(默认使用环境变量配置)
#
# BIN_HOME       程序执行脚步所在路径(默认为启动脚本所在路径)
#
# APP_HOME       进程的安装路径(默认为启动脚本所在父路径)
#
# CLASSPATH      程序类路径(默认为APP_HOME和APP_HOME/lib下面全部jar文件)
#
# ---------------------------------------
APP_SN=AlarmAssociate
JAVA_OPTS='-Xss1024k -Xms512m -Xmx1g'
MAIN_CLASS="com.ailk.module.cms.alarm.alarmassociate.startup.AlarmAssociateStatBootstrap"
LANG=zh_CN.gbk
JAVA_HOME=/export/home/tools/jdk1.6.0_26

# find BIN_HOME
find_BIN_HOME(){
  PRG="$0"
  CUR_PATH=`pwd`
  PRGT=`echo "$PRG"|awk '{print substr($1,1,1)}'`
  if [ $PRGT = "/" ]; then
    # absolute path
    BIN_HOME=`dirname "$PRG"`
  else
    # relative path
    BIN_HOME=`dirname "$CUR_PATH/$PRG"`
  fi
  len=`echo $BIN_HOME|awk '{print length($1)}'`
  if [ $len -gt 2 ]; then
    startindex=`expr $len - 1`
    suffix=`echo $BIN_HOME|awk '{print substr($1,"'"$startindex"'",2)}'`
    if [ $suffix = "/." ]; then
     endindex=`expr $startindex - 1`
     BIN_HOME=`echo $BIN_HOME|awk '{print substr($1,1,"'"$endindex"'")}'`
    fi
  fi
  echo $BIN_HOME
}
BIN_HOME=`find_BIN_HOME`

# find APP_HOME
find_APP_HOME(){
  # find /bin in BIN_HOME
  bin_index=`echo ""|awk '{print index("'"$BIN_HOME"'","/bin")}'`
  if [ $bin_index != 0 ]; then
    # found /bin
    endindex=`expr $bin_index - 1`
    APP_HOME=`echo ""|awk '{print substr("'"$BIN_HOME"'",1,"'"$endindex"'")}'`
  else
    echo "$BIN_HOME don't contain bin folder"
    exit 1
  fi
  echo $APP_HOME
}
APP_HOME=`find_APP_HOME`

# classpath
CLASSPATH=$APP_HOME
for i in `ls $APP_HOME/lib/*.jar`
do
  CLASSPATH=$CLASSPATH:$i
done
CLASSPATH=$CLASSPATH:$APP_HOME/classes

export APP_SN
export BIN_HOME
export APP_HOME
export LANG
export JAVA_HOME
export JAVA_OPTS
export CLASSPATH
export MAIN_CLASS
