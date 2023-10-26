package pantanal.dev.colaboreja.util;

import java.io.File;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class PdfGenerate {
    public static File main(String[] args) {
        // Caminho para o arquivo PDF que você deseja criar
        String nomeArquivoPDF = "exemplo.pdf";

        // Dados para adicionar ao PDF
        String nome = "João da Silva";
        String email = "joao@example.com";

        // Cria um novo documento PDF
        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(nomeArquivoPDF))) {
            // Cria um novo documento
            try (Document doc = new Document(pdfDoc)) {
                // Adiciona um parágrafo com o nome
                Paragraph nomeParagraph = new Paragraph();
                nomeParagraph.add(new Text("Nome: " + nome));
                doc.add(nomeParagraph);

                // Adiciona um parágrafo com o endereço de email
                Paragraph emailParagraph = new Paragraph();
                emailParagraph.add(new Text("Email: " + email));
                doc.add(emailParagraph);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Documento PDF criado com sucesso!");

        // Obtém o objeto File do arquivo PDF criado
        File pdfFile = new File(nomeArquivoPDF);

        if (pdfFile.exists()) {
            System.out.println("Arquivo PDF criado: " + pdfFile.getAbsolutePath());
            return pdfFile;
        } else {
            System.out.println("Não foi possível encontrar o arquivo PDF.");
            return null;
        }
    }
}