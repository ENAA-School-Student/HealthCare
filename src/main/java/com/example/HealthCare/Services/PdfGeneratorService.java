package com.example.HealthCare.Services;

import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGeneratorService {

    public ByteArrayInputStream generateDossierMedicalPdf(DossierMedical dossier) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Dossier Médical", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Patient patient = dossier.getPatient();
            if (patient != null) {
                document.add(new Paragraph("Patient: " + patient.getNom() + " " + patient.getPrenom(), fontContent));
                document.add(new Paragraph("Date de Naissance: " + patient.getDateNaissance(), fontContent));
                document.add(new Paragraph("Téléphone: " + patient.getTelephone(), fontContent));
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph("Diagnostic:", fontTitle));
            document.add(new Paragraph(dossier.getDiagnostic(), fontContent));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Observations:", fontTitle));
            document.add(new Paragraph(dossier.getObservations(), fontContent));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Date de Création: " + dossier.getDateCreation(), fontContent));

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generatePatientAppointmentsPdf(Patient patient, List<RenderVous> appointments) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Liste des Rendez-vous", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Patient: " + patient.getNom() + " " + patient.getPrenom(), fontContent));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 3, 3});

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Date", fontTitle));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Statut", fontTitle));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Médecin", fontTitle));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (RenderVous appointment : appointments) {
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(appointment.getDateRendezVous().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(appointment.getStatut().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                String medecinName = appointment.getMedecine() != null ? appointment.getMedecine().getNom() : "N/A";
                cell = new PdfPCell(new Phrase(medecinName));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generateSimpleReportPdf(String reportTitle, String content) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(reportTitle, fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph(content, fontContent));

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
