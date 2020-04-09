package com.project.rapidline.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProviders;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Adapters.PdfDocumentAdapter;
import com.project.rapidline.utils.Common;
import com.project.rapidline.R;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


public class PrintOutActivity extends AppCompatActivity {

    private SaeedSonsViewModel saeedSonsViewModel;
    private Bails bailData;
    private Customers senderData, receiverData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_out);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);


        //Get Bail to print
        Bundle bundle = getIntent().getExtras();
        String bailId = bundle.getString("itemId");

//        saeedSonsViewModel.getBailById(bailId).observe(this,bails -> {
//            if(bails!=null){
//                bailData=bails;
//                saeedSonsViewModel.getCustById(bailData.getSenderId()).observe(this,sender -> {
//                    senderData=sender;
//                    saeedSonsViewModel.getCustById(bailData.getReceiverId()).observe(this,receiver -> {
//                        receiverData=receiver;
//
//                    });
//                });
//            }
//        });

        Toast.makeText(this, "Item id" + bailId, Toast.LENGTH_SHORT).show();


        //Setup Print button and permissions
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
                                showDialog();
                                loadData(bailId);
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

    private void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading data");
        progressDialog.show();
    }

    private void dismissDialog() {
        progressDialog.dismiss();
    }

    private void removeObservers() {

    }

    private void loadData(String bailId) {
        saeedSonsViewModel.getBailById(bailId).observe(this, bails -> {
            if (bails != null) {
                bailData = bails;
                saeedSonsViewModel.getCustById(bailData.getSenderId()).observe(this, sender -> {
                    senderData = sender;
                    saeedSonsViewModel.getCustById(bailData.getReceiverId()).observe(this, receiver -> {
                        receiverData = receiver;
                        progressDialog.setMessage("Generating PDF");
                        CreatePDFfile(Common.getAppPath(PrintOutActivity.this) + "test_pdf.pdf");
                    });
                });
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void CreatePDFfile(String path) {
        if (new File(path).exists()) {
            new File(path).delete();
        }
        try {

            removeObservers();


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
            BaseColor baseColor = new BaseColor(0, 153, 204, 255);
            float fontsize = 20.0f;
            float valuefontsize = 26.0f;

            BaseFont baseFont = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            //Create Title of document

            //Fonts
            Font titlefont = new Font(baseFont, 45.0f, Font.BOLD, BaseColor.BLACK);
            Font subtitleFont = new Font(baseFont, 36.0f, Font.BOLD, BaseColor.BLACK);
            Font textFont = new Font(baseFont, 30.0f, Font.NORMAL, BaseColor.BLACK);

            addNewItem(document, "Bail Slip", Element.ALIGN_CENTER, titlefont);

            //Builty No
            addLineSeperator(document);
            addNewItem(document, "Bilty No", Element.ALIGN_LEFT, subtitleFont);
            addLineSpace(document);
            addNewItem(document, generateBuiltyNo(), Element.ALIGN_LEFT, textFont);
            addLineSeperator(document);

//                //kind qty
//                AddNewItemWithLeftAndRight(document,"Kind","Quantity",subtitleFont,subtitleFont);
////                AddNewItemWithLeftAndRight(document, bailData.getItemName(),
////                        String.valueOf(bailData.getQuantity()), textFont,textFont);
//                addLineSeperator(document);


            //Invoice Time and date
            AddNewItemWithLeftAndRight(document, "Invoice Time", "Bilty Date", subtitleFont, subtitleFont);
            AddNewItemWithLeftAndRight(document, Calendar.getInstance().getTime().toString(),
                    bailData.getMadeDateTime().toString(), textFont, textFont);
            addLineSeperator(document);


            //From and to Details
            AddNewItemWithLeftAndRight(document, "From", "To", subtitleFont, subtitleFont);
            AddNewItemWithLeftAndRight(document, bailData.getFromCity(),
                    bailData.getToCity(), textFont, textFont);
            addLineSeperator(document);


            createSenderReceiverTable(document);
            addLineSeperator(document);



//            //Transporter
//            addNewItem(document, "Transporter", Element.ALIGN_LEFT, subtitleFont);
//            addLineSpace(document);
////                addNewItem(document,bailData.getTransporterName(),Element.ALIGN_LEFT,textFont);
//            addLineSeperator(document);
//
//            //agent
//            addNewItem(document, "Agent", Element.ALIGN_LEFT, subtitleFont);
//            addLineSpace(document);
////                addNewItem(document,bailData.getName(),Element.ALIGN_LEFT,textFont);
//            addLineSeperator(document);
//
//
//            //Volume weigth
//            AddNewItemWithLeftAndRight(document, "Volume", "Weight", subtitleFont, subtitleFont);
//            AddNewItemWithLeftAndRight(document, bailData.getVolume().toString(),
//                    bailData.getWeight().toString(), textFont, textFont);
//            addLineSeperator(document);

//
//                Font ordernumberfont = new Font(baseFont,36.0f,Font.NORMAL,BaseColor.BLACK);
//
//                Font ordernumbervaluefont = new Font(baseFont,36.0f,Font.NORMAL,BaseColor.BLACK);
//                addNewItem(document,"Order details", Element.ALIGN_CENTER,ordernumbervaluefont);
//
//                addLineSeperator(document);
//
//                addNewItem(document,"Account Name", Element.ALIGN_CENTER,ordernumberfont);
//                addNewItem(document,"Ashar", Element.ALIGN_CENTER,ordernumbervaluefont);
//
//                addLineSeperator(document);
//
//                addLineSpace(document);
//                addNewItem(document,"Product Detail", Element.ALIGN_CENTER,titlefont);
//                addLineSeperator(document);
//
//                AddNewItemWithLeftAndRight(document,"Pizza 25","(0.0%)",titlefont,ordernumbervaluefont);
//                AddNewItemWithLeftAndRight(document,"12.0*1000","12000.0",titlefont,ordernumbervaluefont);
//
//                addLineSeperator(document);
//
//                AddNewItemWithLeftAndRight(document,"Pizza 25","(0.0%)",titlefont,ordernumbervaluefont);
//                AddNewItemWithLeftAndRight(document,"12.0*1000","12000.0",titlefont,ordernumbervaluefont);
//
//                addLineSpace(document);
//                addLineSpace(document);
//
//                AddNewItemWithLeftAndRight(document,"Total","24000.0",titlefont,ordernumbervaluefont);

            document.close();

            dismissDialog();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            printPDF();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter =
                    new PdfDocumentAdapter(PrintOutActivity.this, Common.getAppPath(PrintOutActivity.this) + "test_pdf.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createSenderReceiverTable(Document document) throws IOException, DocumentException{
        PdfPTable table=new PdfPTable(3);
        table.setWidthPercentage(100);

        table.setTotalWidth(new float[]{ 50, 160,160 });

        table.setSkipFirstHeader(true);

        table.addCell("");table.addCell("Sender");table.addCell("Receiver");
        table.addCell("Name");table.addCell(senderData.getCompanyName());table.addCell(receiverData.getCompanyName());
        table.addCell("Address");table.addCell(senderData.getAddress());table.addCell(receiverData.getAddress());
        table.addCell("Phone no");table.addCell(senderData.getCompanyNo());table.addCell(receiverData.getCompanyNo());

        table.addCell("NIC No:");
        document.add(table);
    }

    private PdfPCell customTableCell(String text,float height,int alignment){
        PdfPCell cell=new PdfPCell();
        cell.setPhrase(new Phrase(text));
        cell.setMinimumHeight(40);
        cell.setVerticalAlignment(alignment);
        return cell;
    }

    private void AddNewItemWithLeftAndRight(Document document, String left, String right, Font leftfont, Font RightFont)
            throws DocumentException {
        Chunk leftchunk = new Chunk(left, leftfont);
        Chunk rightchunk = new Chunk(right, RightFont);
        Paragraph paragraph = new Paragraph(leftchunk);

        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(rightchunk);
        document.add(paragraph);
    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 60));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);

    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String order_details, int alignCenter, Font titlefont)
            throws DocumentException {
        Chunk chunk = new Chunk(order_details, titlefont);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(alignCenter);
        document.add(paragraph);

    }

    private String generateBuiltyNo() {
        //Get no
        String builty_str = getApplicationContext().getSharedPreferences("MyPref", 0).getString("builtyNo", "0");
        int builty_no=Integer.parseInt(builty_str);

        //Use the no
        String formatted_num=String.format(Locale.ENGLISH, "%06d", builty_no);

        //Store the next no
        if(builty_no==49999){
         addToPref(String.valueOf(0));
        }
        else {
            int next_no=builty_no+1;
            addToPref(String.valueOf(next_no));
        }

        return formatted_num;

    }

    private void addToPref(String num) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MyPref", 0).edit();
        editor.putString("builtyNo", num);
        editor.apply(); //apply writes the data in background process
    }
}
