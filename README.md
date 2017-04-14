# fgdc2lod

[![Build Status](https://travis-ci.org/ld4l-labs/bib2lod.svg?branch=develop)](https://travis-ci.org/ld4l-labs/bib2lod)
[![Coverage Status](https://coveralls.io/repos/github/ld4l-labs/bib2lod/badge.svg?branch=develop)](https://coveralls.io/github/ld4l-labs/bib2lod)

## Converts FGDC records to RDF.
  
## Build
* Clone the dependency repository from [https://github.com/ld4l-labs/bib2lod]()
* Run `mvn install`
* Clone this repository into an adjacent directory to bib2lod.
* Run `mvn install`
* Copy the executable jar from `target/fgdc2lod.jar` to your preferred work location.
* Copy the example configuration file from `src/main/resources/example.config.json` to your preferred work location. Rename it appropriately. For example, `fgdc.config.json`.

## Configure
* Edit the configuration file to set appropriate input source and output destination.
* Within `InputService`, change the `source` attribute to point either to a single file of MARCXML, or to a directory containing MARCXML files.
  * Each input file must have a filename extension of `.xml`
  * Sample minimal record is in `sample-data/MinimalFGDC_Title.xml`.
* Within `OutputService`, change the `destination` attribute to point to your desired output directory. 
  * _You **must** create this directory before running the program._

## Run
* Execute the jar file, referencing the configuration file on the command line:
  * `java -jar fgdc2lod.jar -c fgdc.config.json`
* Output will be written in N-TRIPLE format to the directory specified in the configuration file. 
  * One output file will be created for each input file. 
  * The name of the output file will be the same as the corresponding input file, but the extension will be `.nt`.
* A log directory will be created as `target/logs` in your work location directory. 
  * A log file of the run will be created as `target/logs/bib2lod.log`
  * An existing log file will not be overwritten, but will be renamed with a timestamp, such as `bib2lod-2017-03-31-14-38-47-1.log`
