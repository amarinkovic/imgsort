[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/402/badge)](https://bestpractices.coreinfrastructure.org/projects/402)

imgsort
=============

*Simple image renaming tool*

This simple Java based command-line utility parses EXIF metadata and reads the time when photo was taken and uses it to sort and rename files chronologically. Potentially, it chould be extended to utilize more of the available metadata.

> _EXIF is short for Exchangeable Image File, a format that is a standard for storing interchange information in digital photography image files used by mostly all new digital cameras. It is used for storing information on the image such as shutter speed, exposure compensation, F number, what metering system was used, if a flash was used, ISO number, date and time the image was taken, white balance, auxiliary lenses that were used and resolution._

Build native binary from source with the following command:
```
mvn clean install -Pnative
```

## Motivation

This tool was made to cope with the issue of having photos of a single event (i.e. road trip, birthday etc.) comming from various sources. Photos are all names using different naming convention and not easy to view in chronologycal order. What this tool does is reads the EXIF information of when the photo was actally taken, if any, otherwise uses a file created timestamp to name the files in chronological sequence.

## Usage instructions

Add natively compiled binary from `target` folder to your path and run it like this:
```shell script
imgsort -d -p myphotos . /tmp/myphotocollection
```

Below is the listing of available command-line arguments you can pass into the app:
```shell script
$ imgsort -h

 ImgSortTool v1.1.0

Usage: <main class> [-dh] [-o=<offset>] [-p=<prefix>] [folders...]
      [folders...]        Source and target folder paths
  -d, --dry-run           Dry run, does not process files, just report
  -h, --help              Display help message
  -o, --offset=<offset>   Counter offset, index for the first element
  -p, --prefix=<prefix>   File naming prefix

```


## Notes

### Cross-compilation
If you happen to need to be able to cross-compile the code, i.e. to make a native linux binary on a mac, run the build from within this container:
```
docker run -it -v $(pwd):/code -w /code --name cross_compiler quay.io/quarkus/centos-quarkus-maven:19.3.1-java11 /bin/sh
```
### Run from source
To run the app directly from source code via maven, issue the following command:
```java
mvn clean compile exec:java -Dexec.mainClass="io.mankea.tools.imgsort.Main" -Dexec.args="-d /media/aleksandar/TerraData/photo/_ajfon"
```
### Native tracing
Append this arguments to the command line:
```java
-agentlib:native-image-agent=trace-output=trace-file.json
```
