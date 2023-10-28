package pantanal.dev.colaboreja.util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PdfGenerate {
    private static final Logger logger = LoggerFactory.getLogger(PdfGenerate.class);

    public static File createContractPDF(SocialActionContractModel socialActionContract) {
        String fileName = generateFileName(socialActionContract);

        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
             Document doc = new Document(pdfDoc, PageSize.A4)) {
            // Add Header
            addHeader(doc);

            // Add Contract Details
            addContractDetails(doc, socialActionContract);

            // Add Collaborator Details
            addCollaboratorDetails(doc, socialActionContract);

            // Add Social Action Details
            addSocialActionDetails(doc, socialActionContract);

            // Add Footer with Terms and Conditions
            addFooter(doc);

            File pdfFile = new File(fileName);
            if (pdfFile.exists()) {
                System.out.println("PDF file created: " + pdfFile.getAbsolutePath());
                return pdfFile;
            } else {
                System.out.println("Failed to create the PDF file.");
                return null;
            }
        } catch (IOException e) {
            logger.error("Ocorreu uma exceção ao criar o PDF", e.getMessage());
            return null;
        }
    }

    private static String generateFileName(SocialActionContractModel socialActionContract) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        return socialActionContract.getColaborator().getFirstname() + "-"
                + socialActionContract.getColaborator().getLastname() + "_"
                + socialActionContract.getSocialActionId().getName() + "_"
                + timestamp + ".pdf";
    }

    private static void addHeader(Document doc) {
        // Customize header content
        doc.add(new Paragraph("Social Action Contract").setFontColor(ColorConstants.RED)
                .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("Colabore Já").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
        doc.add(new LineSeparator(new SolidLine()));
    }

    private static void addContractDetails(Document doc, SocialActionContractModel socialActionContract) {
        doc.add(new Paragraph("Contract Details").setBold().setFontSize(14));
        Table table = new Table(new float[]{1, 3});
        table.useAllAvailableWidth();

        table.addCell(new Cell().add(new Paragraph("Key Process:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getKeyProcess())));

        table.addCell(new Cell().add(new Paragraph("Key Document:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getKeyDocument())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        table.addCell(new Cell().add(new Paragraph("Initial Date & Time:")));
        table.addCell(new Cell().add(new Paragraph(dateFormat.format(socialActionContract.getSocialActionId().getInitDateTime()))));

        table.addCell(new Cell().add(new Paragraph("Finish Date & Time:")));
        table.addCell(new Cell().add(new Paragraph(dateFormat.format(socialActionContract.getSocialActionId().getFinishDateTime()))));


        doc.add(table);
    }

    private static void addCollaboratorDetails(Document doc, SocialActionContractModel socialActionContract) {
        doc.add(new Paragraph("Collaborator Details").setBold().setFontSize(14));
        Table table = new Table(new float[]{1, 3});
        table.useAllAvailableWidth();

        table.addCell(new Cell().add(new Paragraph("Name:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getColaborator().getFirstname()
                + " " + socialActionContract.getColaborator().getLastname())));

        table.addCell(new Cell().add(new Paragraph("Email:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getColaborator().getEmail())));

        table.addCell(new Cell().add(new Paragraph("Action in the social action:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getAction())));

        doc.add(table);
    }

    private static void addSocialActionDetails(Document doc, SocialActionContractModel socialActionContract) {
        doc.add(new Paragraph("Social Action Details").setBold().setFontSize(14));
        Table table = new Table(new float[]{1, 3});
        table.useAllAvailableWidth();

        table.addCell(new Cell().add(new Paragraph("Name:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getSocialActionId().getName())));

        table.addCell(new Cell().add(new Paragraph("Description:")));
        table.addCell(new Cell().add(new Paragraph(socialActionContract.getSocialActionId().getDescription())));

        // Add more details to the table as needed

        doc.add(table);
    }

    private static void addFooter(Document doc) {
        // Customize footer content
        doc.add(new LineSeparator(new SolidLine()));

        // Add terms and conditions
        doc.add(new Paragraph("Terms and Conditions").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER));

        String termsAndConditions = "By participating in this social action, you agree to the following terms and conditions:\n"
                + "1. Participants must adhere to the rules and guidelines of the social action.\n"
                + "2. Respect the rights and privacy of others.\n"
                + "3. Any violation of the terms may result in disqualification from the social action.\n"
                + "4. The organizers reserve the right to make changes to the terms and conditions.";

        doc.add(new Paragraph(termsAndConditions).setFontSize(10));

        // You can customize and add more content to the terms and conditions as needed.
    }

}
