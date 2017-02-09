#!/bin/sh
# ������������������������
# ---------------------------------------
#
# ##### ������ #####
#
# APP_SN         ����Ψһ��ʶ,ͬһ̨���������еĶ���������Ʋ�����ͬ
#
# JAVA_OPTS      JAVA��������
#
# MAIN_CLASS     ����������
#
# ##### ѡ���� #####
#
# LANG           ���԰汾(Ĭ��ʹ�û�����������)
#
# JAVA_HOME      JDK��װĿ¼(Ĭ��ʹ�û�����������)
#
# BIN_HOME       ����ִ�нŲ�����·��(Ĭ��Ϊ�����ű�����·��)
#
# APP_HOME       ���̵İ�װ·��(Ĭ��Ϊ�����ű����ڸ�·��)
#
# CLASSPATH      ������·��(Ĭ��ΪAPP_HOME��APP_HOME/lib����ȫ��jar�ļ�)
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
