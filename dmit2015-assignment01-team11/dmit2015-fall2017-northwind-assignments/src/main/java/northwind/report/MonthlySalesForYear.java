package northwind.report;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlySalesForYear {

	private BigDecimal monthlySales;
	private int month;
	private int Year;
	
	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public MonthlySalesForYear(BigDecimal monthlySales, int month, int year) {
		super();
		this.monthlySales = monthlySales;
		this.month = month;
	}
	public MonthlySalesForYear(Double monthlySales, int month, int year) {
		super();
		this.monthlySales = BigDecimal.valueOf(monthlySales);
		this.month = month;
	}

	public BigDecimal getmonthlySales() {
		return monthlySales;
	}

	public void setTotalSales(BigDecimal monthlySales) {
		this.monthlySales = monthlySales;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	
	
}
