imgrenamer
=============

Simple image renaming tool

EXIF is short for Exchangeable Image File, a format that is a standard for storing interchange information in digital photography image files used by mostly all new digital cameras. It is used for storing information on the image such as shutter speed, exposure compensation, F number, what metering system was used, if a flash was used, ISO number, date and time the image was taken, white balance, auxiliary lenses that were used and resolution.

This simple Java utility parses EXIF metadata and reads the time when photo was taken and uses it to sort and rename files chronologically. Should be extended to utilize more of the available metadata.

```
/usr/lib/jvm/graalvm-ce-java11-19.3.0/bin/native-image \
    -cp /home/aleksandar/.m2/repository/com/drewnoakes/metadata-extractor/2.12.0/metadata-extractor-2.12.0.jar:/home/aleksandar/.m2/repository/com/adobe/xmp/xmpcore/6.0.6/xmpcore-6.0.6.jar:/home/aleksandar/.m2/repository/info/picocli/picocli/4.1.4/picocli-4.1.4.jar:/home/aleksandar/dev/git/image-renamer/target/img-renamer-1.0.0.jar \
    --no-fallback \
    -H:Name=imgrenamer \
    io.mankea.tools.img_renamer.Application
```