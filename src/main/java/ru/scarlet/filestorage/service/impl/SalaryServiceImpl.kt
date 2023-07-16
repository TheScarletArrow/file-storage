package ru.scarlet.filestorage.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import ru.scarlet.filestorage.dto.SalaryIn
import ru.scarlet.filestorage.dto.SalaryOut
import ru.scarlet.filestorage.entity.Salary
import ru.scarlet.filestorage.repository.SalaryRepository
import ru.scarlet.filestorage.service.BasicService
import ru.scarlet.filestorage.service.SalaryService
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*


@Service
open class SalaryServiceImpl(val salaryRepository: SalaryRepository) : SalaryService, BasicService() {
    override fun getSalaryByUserName(user: String): List<Salary> {
        return salaryRepository.findByUserNameOrderByDateAsc(user)
    }

    override fun addSalary(salaryIn: SalaryIn, httpServletRequest: HttpServletRequest): SalaryOut {
        val salary = Salary()
        salary.amount = salaryIn.amount
        salary.date = salaryIn.date
        salary.userName = getUsername(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
        salary.companyName = salaryIn.companyName
        val save = salaryRepository.save(salary)
        return save.toSalaryOut()
    }

    override fun writeToExcel(httpServletRequest: HttpServletRequest): ByteArray {

        val username = getUsername(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
        try {

            val salaryByUserName = getSalaryByUserName(username)
            val workbook = XSSFWorkbook()
            val workSheet = workbook.createSheet()
            val cellStyle = workbook.createCellStyle()
            val doubles = workbook.createCellStyle()

            val cellHeaderStyle = workbook.createCellStyle()
            cellHeaderStyle.fillBackgroundColor = IndexedColors.AQUA.index
            val cellStyl2 = workbook.createCellStyle()
            val dataFormat = workbook.createDataFormat()

            cellStyl2.dataFormat = dataFormat.getFormat("dd.MM.yyyy")


            //createHelper.createDataFormat().getFormat("dd.mm.yyyy")
            val cellDateHeader = workSheet.createRow(0).createCell(0)
            cellDateHeader.setCellValue("Дата зарплаты")
            cellDateHeader.cellStyle = cellHeaderStyle
            val cellAmountHeader = workSheet.getRow(0).createCell(1)
            cellAmountHeader.setCellValue("Сколько пришло")
            val cellCompanyNameHeader = workSheet.getRow(0).createCell(2)
            cellCompanyNameHeader.setCellValue("Название компании")

            salaryByUserName.forEachIndexed { index, it ->
                val createCell: XSSFCell = workSheet
                    .createRow(index + 1)
                    .createCell(0, CellType.NUMERIC)


                createCell.setCellValue(it.date)
                createCell.cellStyle = cellStyl2
                cellStyle.fillForegroundColor = IndexedColors.RED.getIndex()
                cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

                val second = workSheet
                    .getRow(index + 1)
                    .createCell(1)
                second.setCellValue(it.amount.toDouble())
                doubles.dataFormat = dataFormat.getFormat("# ##0.00")
                second.cellStyle=doubles

                val companyName = workSheet
                    .getRow(index + 1)
                    .createCell(2)
                companyName.setCellValue(it.companyName)
            }


            val groupBy = salaryByUserName.sortedBy { it.date.month.value }.groupBy { it.date.month }
            groupBy.onEachIndexed{ index, entry ->

                val createCell = workSheet.createRow(salaryByUserName.size + 2 + index)
                    .createCell(0)
                createCell.setCellValue(entry.key.name)

                val createCell2 = workSheet.getRow(salaryByUserName.size + 2 + index).createCell(1)
                createCell2.setCellValue(entry.value.sumOf { it.amount.toDouble() })
                createCell2.cellStyle=doubles

            }
            val out = ByteArrayOutputStream()
            workbook.write(out)

            out.close()
            workbook.close()

            return out.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ByteArray(1)
    }

}