package com.crm.qa.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFListener implements ITestListener {
	
	/**
     * Document
     */
    private Document document = null;

    /**
     * PdfPTables
     */
    PdfPTable statTable = null, chartTable = null, successTable = null, failTable = null, skipTable = null;

    /**
     * throwableMap
     */
    private HashMap<Integer, Throwable> throwableMap = null;

    /**
     * nbExceptions
     */
    private int nbExceptions = 0;

    /**
     * nbTotalTime
     */
    private long nbTotalTime = 0;


    FileOutputStream fop = null;
    File file;


    /**
     * PDFListener
     */
    public PDFListener() {
        log("PDFListener()");

        this.document = new Document();
        this.throwableMap = new HashMap<Integer, Throwable>();
    }


    /**
     * getResults(ITestResult result)
     */
    public ITestResult getResults(ITestResult result){
        return result;
    }



    /**
     * @see org.testng.ITestListener#onTestSuccess(com.beust.testng.ITestResult)
     */
    public void onTestSuccess(ITestResult result) {
        log("onTestSuccess("+result+")");

        if (successTable == null) {
            this.successTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});

        }

        Paragraph p = new Paragraph("PASSED TESTS  -" + result.getMethod().getMethodName(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.GREEN);
        this.successTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Class"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.successTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Method"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.successTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Time (ms)"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.successTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Status"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.successTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
        this.successTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName()));
        this.successTable.addCell(cell);

        long duration = result.getEndMillis()-result.getStartMillis();
        nbTotalTime += duration;
        cell = new PdfPCell(new Paragraph("" + duration));

        this.successTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("PASSED"));
        this.successTable.addCell(cell);
        //Change messages to steps for use in GUI based WebDriver tests. 
        // The messages are sent via the org.Testng.Report.log method
        p = new Paragraph("Above tests have passed", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.successTable.addCell(cell);
        //p = new Paragraph("" + Reporter.getOutput());
        p.setAlignment(Element.ALIGN_LEFT);
        List unorderedList = new List(List.UNORDERED);
        for(String item:Reporter.getOutput(result)){
            unorderedList.add(item);
        }
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.addElement(unorderedList);
        this.successTable.addCell(cell);
    }

    /**
     * @see com.beust.testng.ITestListener#onTestFailure(com.beust.testng.ITestResult)
     */
    public void onTestFailure(ITestResult result) {
        log("onTestFailure("+result+")");

        if (this.failTable == null) {
            this.failTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});
            this.failTable.setTotalWidth(20f);

        }

        Paragraph p = new Paragraph("FAILED TEST -" + result.getMethod().getMethodName(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.RED);
        this.failTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Class"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Method"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Time (ms)"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Status"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
        this.failTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName()));
        this.failTable.addCell(cell);

        long duration = result.getEndMillis()-result.getStartMillis();
        nbTotalTime += duration;
        cell = new PdfPCell(new Paragraph("" + duration));

        this.failTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("FAILED"));
        this.failTable.addCell(cell);   
        p = new Paragraph("Exception", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
            this.nbExceptions++;
            Paragraph excep = new Paragraph(
                    new Chunk(throwable.toString(), 
                            new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.UNDERLINE)).
                            setLocalGoto("" + throwable.hashCode()));

            p.setAlignment(Element.ALIGN_LEFT);
            cell = new PdfPCell(excep);
            cell.setColspan(4);
            this.failTable.addCell(cell);
        }

        p = new Paragraph("TEST STEPS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.failTable.addCell(cell);
        p = new Paragraph("" + Reporter.getOutput());
        p.setAlignment(Element.ALIGN_LEFT);
        List unorderedList = new List(List.UNORDERED);
        for(String item:Reporter.getOutput(result)){
            unorderedList.add(item);
        }
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.addElement(unorderedList);
        this.failTable.addCell(cell);
    }

    /**
     * @see com.beust.testng.ITestListener#onTestSkipped(com.beust.testng.ITestResult)
     */
    public void onTestSkipped(ITestResult result) {
        log("onTestSkipped("+result+")");
        if (this.skipTable == null) {
            this.skipTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});
            this.skipTable.setTotalWidth(20f);
        }

//        Paragraph p = new Paragraph("SKIPPED TESTS  -" + result.getMethod().getDescription(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        Paragraph p = new Paragraph("SKIPPED TESTS  -" + result.getMethod().getMethodName(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.YELLOW);
        this.skipTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
//        cell = new PdfPCell(new Paragraph(result.getTestClass().getName() + "." + result.getMethod()));
        cell.setColspan(4);
        this.skipTable.addCell(cell);
    }

    /**
     * @see com.beust.testng.ITestListener#onStart(com.beust.testng.ITestContext)
     */
    public void onStart(ITestContext context) {
        log("onStart("+context+")");
        file = new File(System.getProperty("user.dir") + "/test-output/TestReport.pdf");
        // if file doesn't exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PdfWriter.getInstance(this.document, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.document.open();

        Paragraph p = new Paragraph(context.getName(),
                FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));

        try {
            this.document.add(p);
            this.document.add(new Paragraph(new Date().toString()));
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }



    }

    /**
     * @see com.beust.testng.ITestListener#onFinish(com.beust.testng.ITestContext)
     */
    public void onFinish(ITestContext context) {
        log("onFinish("+context+")");

        if (statTable == null) {
            this.statTable = new PdfPTable(new float[]{.3f, .2f, .2f, .2f, .3f});   
        }

        if (chartTable == null) {
            this.chartTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});   
        }

        Paragraph p = new Paragraph("STATISTICS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(p);
        cell.setColspan(5);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Passed"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Skipped"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Failed"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Pass Percent"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("Total Time"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.statTable.addCell(cell);

        int passed = context.getPassedTests().size();
        int skipped = context.getSkippedTests().size();
        int failed = context.getFailedTests().size();
        double total = passed + skipped + failed;
        double percent = ((int)passed/total) * 100;

        cell = new PdfPCell(new Paragraph("" + passed));
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("" + skipped));
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("" + failed));
        this.statTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("" + percent));
        this.statTable.addCell(cell);   
        cell = new PdfPCell(new Paragraph("" + nbTotalTime));
        this.statTable.addCell(cell);       


        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("Failed", failed);
        dataSet.setValue("Skipped", skipped);
        dataSet.setValue("Passed", passed);
        p = new Paragraph("Data Chart", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        this.chartTable.addCell(cell);

    JFreeChart chart = ChartFactory.createPieChart3D(
                "Test Success Rate", dataSet, true, true, false);

    java.awt.Image originalImage = chart.createBufferedImage(500, 300);
    com.lowagie.text.Image image1 = null;
    try {
        image1 = com.lowagie.text.Image.getInstance(originalImage,Color.white);
    } catch (BadElementException e4) {
        e4.printStackTrace();
    } catch (IOException e4) {
        e4.printStackTrace();
    }

        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.addElement(image1);

        this.chartTable.addCell(cell);


        try {
            if (this.statTable != null) {
                log("Added Statistics table");
                this.statTable.setSpacingBefore(15f);
                this.document.add(this.statTable);
                this.statTable.setSpacingAfter(15f);
            }

            if (this.chartTable != null) {
                log("Added chart table");
                this.chartTable.setSpacingBefore(15f);
                this.document.add(this.chartTable);
                this.chartTable.setSpacingAfter(15f);
            }

            if (this.failTable != null) {
                log("Added fail table");
                this.failTable.setSpacingBefore(15f);
                this.document.add(this.failTable);
                this.failTable.setSpacingAfter(15f);
            }

            if (this.successTable != null) {
                log("Added success table");
                this.successTable.setSpacingBefore(15f);
                this.document.add(this.successTable);
                this.successTable.setSpacingBefore(15f);
            }

            if (this.skipTable != null) {
                log("Added skip table");
                this.skipTable.setSpacingBefore(15f);
                this.document.add(this.skipTable);
                this.skipTable.setSpacingBefore(15f);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //Commenting out the exceptions summary
      /*  p = new Paragraph("EXCEPTIONS SUMMARY",
                FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
        try {
            this.document.add(p);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }

        Set<Integer> keys = this.throwableMap.keySet();

        assert keys.size() == this.nbExceptions;

        for(Integer key : keys) {
            Throwable throwable = this.throwableMap.get(key);

            Chunk chunk = new Chunk(throwable.toString(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(255, 0, 0)));
            chunk.setLocalDestination("" + key);
            Paragraph throwTitlePara = new Paragraph(chunk);
            try {
                this.document.add(throwTitlePara);
            } catch (DocumentException e3) {
                e3.printStackTrace();
            }

            StackTraceElement[] elems = throwable.getStackTrace();
            String exception = "";
            for(StackTraceElement ste : elems) {
                Paragraph throwParagraph = new Paragraph(ste.toString());
                try {
                    this.document.add(throwParagraph);
                } catch (DocumentException e2) {
                    e2.printStackTrace();
                }
            }
        } */

        this.document.close();
    }

    /**
     * log
     * @param o
     */
    public static void log(Object o) {
        //System.out.println("[JyperionListener] " + o);
    }

    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

}
