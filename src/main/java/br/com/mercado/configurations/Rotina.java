package br.com.mercado.configurations;

import br.com.mercado.model.entity.Pedido;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.service.EmailSenderService;
import br.com.mercado.service.PedidoService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
@Log4j2
public class Rotina {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    EmailSenderService emailSenderService;


    //atributos para a alteracao do produto? minuto, horas, produto, até quando...

    //todos os dias, 6 dias por semana as 20h ira enviar um email - relatorio de vendas do dia
    @Scheduled(cron = "0 21 8 * * ?", zone = "America/Sao_Paulo")
    public void enviarEmail() throws MessagingException, FileNotFoundException {
        log.info("Enviar planilha para o email as 20h de todos os dias");

        gerarPlanilha(); //DataSource


        LocalDate data = LocalDate.now();
        emailSenderService.sendEmailWithAttachment("brunoberga77@gmail.com",
                "Segue em anexo o relatorio diario", "RELATORIO MERCADO DIA " + data.toString(),
                "/Users/brunobergamasco/downloads/pedidos.xls");
    }

//    @Scheduled(cron = "0 ?1 ?2 3? 4? ?", zone = "America/Sao_Paulo")
//    public void alterarProduto(int minuto, int hora, int dia, int mes){
//
//    alterarProduto(minuto, hora, dia+5) // apos uma semana alterar o valor para o normal
//
//    }

    public FileOutputStream gerarPlanilha() {

        List<Pedido> pedidos = pedidoService.pedidosDiarios();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("pedidos");

        //padroes de layout
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeight((short) 400);

        int rownum = 0;
        int cellnum = 0;
        Cell cell;
        Row row;

        //configurando estilos de celulas(cores, alinhamentos, formatacao)
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberStyle.getDataFormat());
        numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //configurando Header
        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Codigo do pedido");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Data do pedido");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Status do pedido");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Valor total do pedido");

        double valorTotalDiario = 0;

        for (Pedido pedido : pedidos) {
            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(pedido.getId());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(numberStyle);
            cell.setCellValue(pedido.getDataPedido().toString());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(pedido.getStatusDoPedido());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(numberStyle);
            double valorTotal = pedido.getValorTotal();
            cell.setCellValue(valorTotal);

            valorTotalDiario += pedido.getValorTotal();
        }

//        //qual produto mais vendido?
//        String nomeProduto = null;
//        int quantity = 0;
//        for (int i = 0; i < pedidos.size(); i++) {
//            for (int j = 0; j < pedidos.get(i).getItens().size() - 1; j++) {
//                quantity = pedidos.get(i).getItens().get(j).getQuantidade();
//                nomeProduto = pedidos.get(i).getItens().get(j + 1).getProduto().getNome();
//                if (quantity < pedidos.get(i).getItens().get(j + 1).getQuantidade()) {
//                    nomeProduto = pedidos.get(i).getItens().get(j + 1).getProduto().getNome();
//                    quantity = pedidos.get(i).getItens().get(j).getQuantidade();
//                }
//            }
//        }

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Valor total diario");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(numberStyle);
        cell.setCellValue(valorTotalDiario);


        //PRODUTO MAIS VENDIDO:x
//        row = sheet.createRow(rownum++);
//        cellnum = 4;
//        cell = row.createCell(cellnum++);
//        cell.setCellStyle(headerStyle);
//        cell.setCellValue("Produto mais vendido");
//        cell = row.createCell(cellnum++);
//        cell.setCellStyle(textStyle);
//        cell.setCellValue(nomeProduto);
//
//        cell = row.createCell(cellnum++);
//        cell.setCellStyle(numberStyle);
//        cell.setCellValue(quantity);

        try {
            FileOutputStream out = new FileOutputStream(new File("/Users/brunobergamasco/downloads/pedidos.xls"));
            workbook.write(out);
            out.close();
            workbook.close();
            return out;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

