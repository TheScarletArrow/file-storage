package ru.scarlet.filestorage.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
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
import java.time.format.DateTimeFormatter
import java.util.*


@Service
open class SalaryServiceImpl(val salaryRepository: SalaryRepository) : SalaryService, BasicService() {
    override fun getSalaryByUserName(user: String): List<Salary> {
        return salaryRepository.findByUserNameOrderByDateDesc(user)
    }

    override fun addSalary(salaryIn: SalaryIn, httpServletRequest: HttpServletRequest): SalaryOut {
        val salary = Salary()
        salary.amount = salaryIn.amount
        salary.date = salaryIn.date
        salary.userName = getUsername(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))

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

            val cellStyl2 = workbook.createCellStyle()
            val createHelper: CreationHelper = workbook.creationHelper
            val dataFormat = workbook.createDataFormat()

            cellStyl2.dataFormat= dataFormat.getFormat("dd.MM.yyyy")



            //createHelper.createDataFormat().getFormat("dd.mm.yyyy")

            salaryByUserName.forEachIndexed { index, it ->
                val createCell: XSSFCell = workSheet
                    .createRow(index)
                    .createCell(0, CellType.NUMERIC)


                createCell.setCellValue(it.date)
                createCell.cellStyle = cellStyl2





                cellStyle.fillForegroundColor = IndexedColors.RED.getIndex()
                cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
                val second = workSheet
                    .getRow(index)
                    .createCell(1)
                second.setCellValue(it.amount.toDouble())

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