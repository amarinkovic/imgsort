imgrenamer
=============

Simple image renaming tool

EXIF is short for Exchangeable Image File, a format that is a standard for storing interchange information in digital photography image files used by mostly all new digital cameras. It is used for storing information on the image such as shutter speed, exposure compensation, F number, what metering system was used, if a flash was used, ISO number, date and time the image was taken, white balance, auxiliary lenses that were used and resolution.

This simple Java utility parses EXIF metadata and reads the time when photo was taken and uses it to sort and rename files chronologically. Should be extended to utilize more of the available metadata.

Build native app from source
```
mvn clean install -Pnative
```

## Notes

Run app from source via maven
```java
mvn clean compile exec:java -Dexec.mainClass="io.mankea.tools.img_renamer.Application" -Dexec.args="-d /media/aleksandar/TerraData/photo/_ajfon"
```

Native tracing args:
```java
-agentlib:native-image-agent=trace-output=trace-file.json
```
