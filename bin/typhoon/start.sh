#!/bin/sh
# ---------------------------------------
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

# ---------------------------------------
# use setenv.sh
SETENV="$BIN_HOME"/setenv.sh
if [ -r "$SETENV" ]; then
  . "$SETENV"
else
  echo "Cannot find $SETENV"
  echo "This file is needed to run this program"
  exit 1
fi

# ---------------------------------------
# check force start? no confirm
force=false
for p in $*;
do
  if [ $p = "-f" ]; then
    force=true
  fi
done;
if [ $force != true ]; then
  # confirm to start 
  echo -e "Are you sure to start $APP_SN ?[y/n]"
  read input
  case $input in
    y|Y)
      echo "prepare to start $APP_SN, please wait ... "
    ;;
    n|N)
      exit 1
    ;;
    *)
      exit 1
  esac
fi

# ---------------------------------------
# kill old process
APP_PROCESS_NO=`ps -ef|grep $APP_SN|grep -v "grep"|awk '{print $2}'`
while [ -n "$APP_PROCESS_NO" ] ; do
  echo "$APP_SN process aleary exist, must kill first, process No. " $APP_PROCESS_NO
  kill -9 $APP_PROCESS_NO
  sleep 1
  APP_PROCESS_NO=`ps -ef|grep $APP_SN|grep -v "grep"|awk '{print $2}'`
done
echo "$APP_SN process not exist."

# ---------------------------------------
# print env
if [ -r "$BIN_HOME"/printenv.sh ]; then
  . "$BIN_HOME"/printenv.sh
else
  echo "Cannot find $BIN_HOME/printenv.sh"
fi

# Start
echo "$APP_SN starting ..."
RUN_OPTS="-DAPP_SN=$APP_SN $JAVA_OPTS -DAPP_HOME=$APP_HOME -classpath $CLASSPATH"
nohup $JAVA_HOME/bin/java $RUN_OPTS $MAIN_CLASS > $BIN_HOME/$APP_SN.out &
echo "$APP_SN started."

