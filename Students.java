import java.util.Base64;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Students {
	private int id;
	private String name;
	private String address;
	private String birthday;

	public Students(int id, String name, String address, String birthday) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.birthday = birthday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int calculateAge() {
		int birthYear = Integer.parseInt(birthday.split("-")[0]);
		int currentYear = java.time.LocalDate.now().getYear();
		return currentYear - birthYear;
	}

	public long calculateDaysSinceBirth() {
		java.time.LocalDate birthDate = java.time.LocalDate.parse(birthday);
		java.time.LocalDate currentDate = java.time.LocalDate.now();
		return java.time.temporal.ChronoUnit.DAYS.between(birthDate, currentDate);
	}

    public long calculateYearsSinceBirth() {
        LocalDate birthDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.YEARS.between(birthDate, currentDate);
    }
	
	public int calculateMonthsSinceBirth() {
	    LocalDate birthDate = LocalDate.parse(birthday);
	    LocalDate currentDate = LocalDate.now();
	    Period period = Period.between(birthDate, currentDate);
	    return period.getMonths();
	}

	public String encodeBirthDate() {
		String encodedDate = Base64.getEncoder().encodeToString(birthday.getBytes());
		return encodedDate;
	}

	public boolean isPrimeYear() {
		int year = Integer.parseInt(birthday.split("-")[0]);
		if (year <= 1) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(year); i++) {
			if (year % i == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Students{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", birthday='" + birthday + '\'' +
				'}';
	}
}
