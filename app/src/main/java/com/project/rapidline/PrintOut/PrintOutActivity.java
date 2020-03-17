package com.project.rapidline.PrintOut;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.itextpdf.text.pdf.parser.Line;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.project.rapidline.Models.Common;
import com.project.rapidline.R;

import java.io.File;
import java.io.FileOutputStream;


public class PrintOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_out);
        final Button printBtn = findViewById(R.id.print_btn);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        printBtn.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                CreatePDFfile(Common.getAppPath(PrintOutActivity.this) + "test_pdf.pdf");
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void CreatePDFfile(String path) {
        if(new File(path).exists()) {
            new File(path).delete();
        }
            try{
                Document document = new Document();
                //Save
                PdfWriter.getInstance(document, new FileOutputStream(path));
                //Open to write
                document.open();

                //settings
                document.setPageSize(PageSize.A4);
                document.addCreationDate();
                document.addAuthor("2 minut Networks");
                document.addCreator("Ashar");

                //Font Settins
                BaseColor baseColor = new BaseColor(0,153,204,255);
                float fontsize = 20.0f;
                float valuefontsize = 26.0f;

                BaseFont baseFont = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8",BaseFont.EMBEDDED);
                //Create Title of document
                Font titlefont = new Font(baseFont,36.0f,Font.NORMAL,BaseColor.BLACK);
                addNewItem(document,"Order details", Element.ALIGN_CENTER,titlefont);

                Font ordernumberfont = new Font(baseFont,36.0f,Font.NORMAL,BaseColor.BLACK);
                addNewItem(document,"Order details", Element.ALIGN_CENTER,ordernumberfont);

                Font ordernumbervaluefont = new Font(baseFont,36.0f,Font.NORMAL,BaseColor.BLACK);
                addNewItem(document,"Order details", Element.ALIGN_CENTER,ordernumbervaluefont);

                addLineSeperator(document);

                addNewItem(document,"Account Name", Element.ALIGN_CENTER,ordernumberfont);
                addNewItem(document,"Ashar", Element.ALIGN_CENTER,ordernumbervaluefont);

                addLineSeperator(document);

                addLineSpace(document);
                addNewItem(document,"Product Detail", Element.ALIGN_CENTER,titlefont);
                addLineSeperator(document);

                AddNewItemWithLeftAndRight(document,"Pizza 25","(0.0%)",titlefont,ordernumbervaluefont);
                AddNewItemWithLeftAndRight(document,"12.0*1000","12000.0",titlefont,ordernumbervaluefont);

                addLineSeperator(document);

                AddNewItemWithLeftAndRight(document,"Pizza 25","(0.0%)",titlefont,ordernumbervaluefont);
                AddNewItemWithLeftAndRight(document,"12.0*1000","12000.0",titlefont,ordernumbervaluefont);

                addLineSpace(document);
                addLineSpace(document);

                AddNewItemWithLeftAndRight(document,"Total","24000.0",titlefont,ordernumbervaluefont);

                document.close();

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                printPDF();

            }catch (Exception e){
                e.printStackTrace();
            }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try{
            PrintDocumentAdapter printDocumentAdapter =
                    new PdfDocumentAdapter(PrintOutActivity.this,Common.getAppPath(PrintOutActivity.this) + "test_pdf.pdf");
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void AddNewItemWithLeftAndRight(Document document, String left, String right, Font leftfont,Font RightFont)
            throws DocumentException {
        Chunk leftchunk = new Chunk(left,leftfont);
        Chunk rightchunk = new Chunk(right,RightFont);
        Paragraph paragraph = new Paragraph(leftchunk);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(rightchunk);
        document.add(paragraph);

    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0,60));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);

    }
    private void addLineSpace(Document document)throws DocumentException{
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String order_details, int alignCenter, Font titlefont)
                            throws DocumentException {
        Chunk chunk = new Chunk(order_details,titlefont);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(alignCenter);
        document.add(paragraph);

    }
}
