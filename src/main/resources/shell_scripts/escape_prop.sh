#!/bin/sh

# target property list - place 'double back slash' (before '.') & (after '=')
array=(
		roms\\.api\\.username=\\
		roms\\.api\\.password=\\
		roms\\.api\\.file\\.download\\.base\\.url=\\
		thesisdeposit\\.sword\\.username=\\
		thesisdeposit\\.sword\\.password=\\
		thesisdeposit\\.sword\\.collection\\.handle=\\
		local\\.datasource\\.username=\\
		local\\.datasource\\.password=\\
      )

#export working_dir=`realpath ./src/main/resources/`
export working_dir=`realpath ./dev_conf/`
export src_file=application.properties

export replaceString=*****
export tmp_file=${src_file}_tmp
export target_file=${src_file}_sample

cp ${working_dir}/${src_file} ${working_dir}/${tmp_file}

for i in "${array[@]}"
do
        sed "s/\($i).*\$/\1${replaceString}/" ${working_dir}/${tmp_file} > ${working_dir}/${target_file}
        cp ${working_dir}/${target_file} ${working_dir}/${tmp_file}
done

cp ${working_dir}/${target_file} ./src/main/resources/${target_file}
rm ${working_dir}/${tmp_file} ${working_dir}/${target_file}
