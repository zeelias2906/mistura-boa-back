package com.mistura_boa.mistura_boa.services;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mistura_boa.mistura_boa.models.enums.FormaPagamentoEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpressaoService {

    private final PedidoService pedidoService;

    public ResponseEntity<byte[]> imprimirPedido(Long idPedido) throws Exception {
        var pedido = pedidoService.getById(idPedido);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            ImageData imageData = ImageDataFactory.create("src/main/resources/imgs/logo.jpg");
            Image logo = new Image(imageData).setTextAlignment(TextAlignment.CENTER).scaleToFit(100, 100).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        } catch (Exception e) {
            // opcional: ignorar se não encontrar a imagem
        }

        document.add(new Paragraph("Pedido")
            .setFontSize(18)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(10));

        document.add(new Paragraph("Número: " + pedido.getNumeroPedido().toString()).setMarginBottom(5));
        document.add(new Paragraph("Cliente: " + pedido.getUsuario().getPessoa().getNome()).setMarginBottom(5));
        LocalDateTime dataPedido = pedido.getDataPedido();
        String dataFormatada = dataPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        document.add(new Paragraph("Data: " + dataFormatada).setMarginBottom(10));


        document.add(new Paragraph("Produtos")
        .setFontSize(18)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));
        
        Table table = new Table(UnitValue.createPercentArray(new float[]{40, 40, 20})).useAllAvailableWidth();

        Cell produto = new Cell().add(new Paragraph("Produto")).setBorder(Border.NO_BORDER);
        Cell observacao = new Cell().add(new Paragraph("Observação")).setBorder(Border.NO_BORDER);
        Cell valor = new Cell().add(new Paragraph("Valor")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);

        table.addHeaderCell(produto);
        table.addHeaderCell(observacao);
        table.addHeaderCell(valor);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        for(var produtoPedido:pedido.getProdutosPedido()){
            Cell Nomeproduto = new Cell().add(new Paragraph(produtoPedido.getProduto().getNome())).setBorder(Border.NO_BORDER);
            Cell observacaoTxt = new Cell().add(new Paragraph(produtoPedido.getObservacao() == null ? "" : produtoPedido.getObservacao())).setBorder(Border.NO_BORDER);
            Cell valorProd = new Cell().add(new Paragraph(formatter.format(produtoPedido.getProduto().getValor()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);

            table.addCell(Nomeproduto);
            table.addCell(observacaoTxt);
            table.addCell(valorProd);
        }

        document.add(table);
        
        document.add(new Paragraph("TOTAL: " + formatter.format(pedido.getValor())).setBold().setTextAlignment(TextAlignment.RIGHT).setMarginTop(20));

        document.add(new Paragraph("Forma de pagamento")
        .setFontSize(18)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));

        document.add(new Paragraph(pedido.getFormaPagamento().getDescricao()).setMarginBottom(1));

        if(pedido.getFormaPagamento() == FormaPagamentoEnum.DINHEIRO){
            if(pedido.getPrecisaTroco()){
                document.add(new Paragraph("Troco para: " + formatter.format(pedido.getValorTroco())).setMarginBottom(10));
            }else{
                document.add(new Paragraph("Não precisa de troco").setMarginBottom(10));
            }
        }

        document.add(new Paragraph("Entrega")
        .setFontSize(18)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));

        if(pedido.getEndereco()==null){
            document.add(new Paragraph("Pedido será retirado no estabelecimento").setMarginBottom(10));
        }else{
            var endereco = pedido.getEndereco();
            document.add(new Paragraph("Endereço para entrega: " + endereco.getLogradouro() + " - " +  endereco.getBairro() + ", n° "  + endereco.getNumero()).setMarginBottom(1));
            document.add(new Paragraph("Ponto de referência: " + (endereco.getPontoReferencia()== null ? "Não possui ponto de referência" : endereco.getPontoReferencia())).setMarginBottom(1));
            document.add(new Paragraph("Telefone para contato: " + pedido.getUsuario().getPessoa().getTelefone()).setMarginBottom(10));
        }

        document.close();
        byte[] pdfBytes = out.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "pedido_" + pedido.getNumeroPedido() + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
