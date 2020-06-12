# Export CAD files

Space Manager needs to be able to view context to export multiple CAD drawings based on current filter so that they can quickly provide on request.

- Need to create an extension to do this in Planon

- Web extension partially exists (pulls filters in Planon)
Still need to develop grabbing those drawings and exporting them.
- Planon is in the Cloud; need to work out how to get them from the cloud to my desktop

- Need to review reference date interaction in Tom's version (want to confirm that drawings are the most current version)

## One time setup

### Create gradle.properties file to specify Java version

```bash
export $REPO_LOCATION='/Users/f003r9n/data/code/dartgit/planon/tms/planon-downloadcadfiles-wcx'
cd $REPO_LOCATION
touch gradle.properties
echo "org.gradle.java.home=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home" >> gradle.properties
```

### Create a lib folder with all dependencies inside

```bash
cd $REPO_LOCATION
mkdir lib
```

The lib folder should contain the following jar files:

- osgi.core-7.0.0.jar
- com.planonsoftware.webclientextension.api-2.1.3.- 0-5.jar
- com.planonsoftware.logging-4.0.5.0-1.jar
- log4j-1.2.17.jar
- wicket-core-8.1.0.jar
- wicket-extensions-8.1.0.jar
- wicket-request-8.1.0.jar
- wicket-util-8.1.0.jar
- jdk-serializable-functional-1.8.6.jar
- com.planonsoftware.enterprise.service.api-2.25.1.- 0-1.jar
- com.planonsoftware.enterprise.service.api.impl-7.0.- 19.0-1.jar

## Run

```bash
gradle build
```

## Put the created ExportCadFiles-WCX-1.0.0.jar file to Planon

### first time setup

- Copy the resulting JAR from **build/libs/** to the **/tms/upload/jboss-web** folder on the Planon server

- log in to Planon

- As Planon Administrator in Environment Management use Reload TMS (it may be necessary to also Restart Planon Instance)
    1. Log In
    2. Environment Management > Danger Zone > Reload TMS
    3. Environment Management > Logs > TMS-deployed.out look for jboss-web:HelloWorld-XA-1.0.0.jar

- Business process > Field Definer
  1. Select Business object to attach XA
  2. Put BO Under Construction
  3. Details > Extended Actions
  4. Add
     1. Web2Client class name: edu.dartmouth.bt.planon.exportcadfiles.wcx.ExportCadFilesWCX
     2. System name: ExportCadFilesWCX
     3. Code: ExportCadFilesWCX
     4. Icon: </choose one>
     5. Description: </enter short description>
     6. Save
     7. Translation: Export CAD Files
     8. Save
  5. Put BO Completed
  6. With BO selected...
       1. Jump to Layouts
       2. Select Floors
       3. Put Layout Under Construction
            1. Select Actions
            2. Choose ExportCadFilesWCX from Extended actions menu
            3. Drag & Drop on Actions Layout section
            4. Save
            5. Put Layout Completed

### For each run

- Close any existing browser sessions with Planon

- ```bash
  gradle -q buildWithURL
  ```

### How to test

- Make sure you have Space Manager Access in Planon
- Space Planning > Spaces & Workspaces > Spaces
- Apply any filter
- Select All
- Click Go to "Floor"
- Select All
- Click "Export CAD Files"
