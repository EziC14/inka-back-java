package com.rest.web.inka.service;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.Producto;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf-resources/";

    private final org.thymeleaf.spring6.SpringTemplateEngine springTemplateEngine;
    private final ProductoService productoService;
    private final MovimientoService movimientoService;

    public PdfService(org.thymeleaf.spring6.SpringTemplateEngine springTemplateEngine, ProductoService productoService, MovimientoService movimientoService) {
        this.springTemplateEngine = springTemplateEngine;
        this.productoService = productoService;
        this.movimientoService = movimientoService;
    }

    public File generateProductoPdf() throws Exception {
        return generatePdf("report_stock", getContextProductoListPdf());
    }

    public File generateMovimientoPdf() throws Exception {
        return generatePdf("report_movimiento", getContextMovimientoListPdf());
    }

    private File generatePdf(String templateName, Context context) throws Exception {
        String html = loadAndFillTemplate(templateName, context);
        String xhtml = convertToXhtml(html);
        return renderPdf(xhtml, templateName);
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        org.w3c.dom.Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);
        return out.toString();
    }

    private File renderPdf(String html, String templateName) throws Exception {
        File file = File.createTempFile(templateName, ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContextProductoListPdf() {
        List<Producto> productos = productoService.list();
        Context context = new Context();
        context.setVariable("productos", productos);
        return context;
    }

    private Context getContextMovimientoListPdf() {
        List<Movimiento> movimientos = movimientoService.getListMovimiento();
        Context context = new Context();
        context.setVariable("movimientos", movimientos);
        return context;
    }

    private String loadAndFillTemplate(String templateName, Context context) {
        return springTemplateEngine.process(templateName, context);
    }
    
    // Métodos adicionales para otros servicios
    public File generateCustomPdf(String templateName, Context context) throws Exception {
        return generatePdf(templateName, context);
    }
}