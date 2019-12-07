@SET FOLDER_PATH=d:/other/2014-03-01-Scotland
@SET NAME_PREFIX=Scotland

@java -cp image-renamer.jar com.am.image.tools.Renamer %FOLDER_PATH% %NAME_PREFIX%

@pause
