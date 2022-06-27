#/bin/bash
project_name=springboot-html
echo $project_name
ps aux | grep $project_name | grep -v grep | awk '{print $2}' | xargs kill -15
