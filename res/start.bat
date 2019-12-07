@SET SOURCE_FOLDER_PATH=d:/other/2014-03-01-Scotland
@SET TARGET_FOLDER_PATH=d:/temp
@SET NAME_PREFIX=Scotland

@java -cp image-renamer.jar com.am.image.tools.Renamer %SOURCE_FOLDER_PATH% %TARGET_FOLDER_PATH% %NAME_PREFIX% > renamer.log

@pause
