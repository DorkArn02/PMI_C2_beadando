package utils;

public class LanguageUtils {
	/**
	 * Itt lehet megváltoztatni a swing komponensek szövegét
	 */
	// Dashboard.java
	public static String MEDICAL_HISTORY = "Kórtörténet / adatok";
	public static String LIST_EXPORT = "Páciens lista / export";
	public static String ADD_NEW_DIAGNOSIS = "Betegség rögzítése";
	public static String EDIT_PATIENT_DATA = "Páciens adatmódosítás";
	public static String DELETE_PATIENT_DATA = "Páciens törlése";
	public static String ADD_NEW_PATIENT = "Páciens hozzáadása";
	public static String MAIN_TITLE = "Egészségügyi nyilvántartó rendszer";
	public static String CONFIRM_EXIT = "Biztosan ki szeretne lépni a programból!";
	public static String EXIT_LABEL = "Kilépés";
	
	public static String DASHBOARD_TITLE = "E-med";
	public static String ADD_DISEASE_TITLE = "Diagnózis rögzítése a páciens kórtörténetéhez";

	// AddPatient.java AddDisease.java
	public static String ADD_PATIENT_TITLE = "Páciens hozzáadása";
	public static String ADD_PATIENT_BTN = "Hozzáadás";
	public static String ERROR_EMPTY_FIELDS = "Kérem ne hagyjon egy mezőt sem üresen!";
	public static String PATIENT_ADDED_SUCCESSFULLY = "Páciens adatai sikeresen rögzítve!";
	public static String CONTAINS_PATIENT = "Ilyen páciens már szerepel az adatbázisban!";
	public static String FIRST_NAME_LABEL = "Keresztnév";
	public static String LAST_NAME_LABEL = "Vezetéknév";
	public static String BORN_PLACE_LABEL = "Születési hely";
	public static String BORN_DATE_LABEL = "Születési dátum";
	public static String FATHER_NAME_LABEL = "Apja neve";
	public static String MOTHER_NAME_LABEL = "Anyja neve";
	public static String CITY_LABEL = "Település";
	public static String STREET_LABEL = "Utca";
	public static String HOUSE_NUMBER_LABEL = "Házszám";
	public static String PHONE_NUMBER_LABEL = "Telefonszám";
	public static String TAJ_NUMBER_LABEL = "Taj szám";
	public static String ZIP_CODE_LABEL = "Irányítószám";
	public static String MAIDEN_NAME_LABEL = "Leánykori név";
	public static String GENDER_LABEL = "Neme";
	public static String ERROR_WRONG_DATE_FORMAT = "Kérem helyes formátumban adja meg a születési dátumot: yyyy/MM/dd!";
	public static String ERROR_WRONG_TAJ_FORMAT = "Kérem helyes formátumban adja meg a taj számot (9 számjegyből álló kód)!";
	public static String SEARCH_PATIENT_BTN = "Keresés";
	public static String CONFIRM_EXIT_DATA = "Biztos megszakítja az adatbevitelt? Az adatmezők törlődni fognak.";
	public static String MEDICAL_HISTORY_2 = "Kórtörténet";
	public static String ERROR_WRONG_ZIP_FORMAT = "Kérem egy négy számjegyű irányítószámot adjon meg!";
	public static String ERROR_WRONG_HOUSE_NUMBER_FORMAT = "Kérem egy házszámot adjon meg!";

	// AddDisease.java
	public static String DIAGNOSIS_LABEL = "Diagnózis";
	public static String EXPERT_OPINION_LABEL = "Szakértői vélemény";
	public static String DIAGNOSIS_DATE_LABEL = "Rögzítés dátuma";
	public static String ADD_DIAGNOSIS_BTN = "Rögzítés";
	public static String PATIENT_NOT_EXISTS = "Ilyen páciens nem szerepel az adatbázisban!";
	public static String DIAGNOSIS_EXISTS = "Ezen a dátumon már rögzített diagnózist!";
	public static String DIAGNOSIS_NOT_EXISTS = "Egy diagnózist sem rögzítettek ehhez a pácienshez!";
	public static String DIAGNOSIS_DELETED = "Sikeresen törölte a kiválaszott diagnózist a páciens adatai közül!";
	public static String ERROR_NO_SEARCH = "Kérem előszőr írja be a páciens TAJ számát és kattintson keresés gombra!";
	public static String DIAGNOSIS_DELETE_LABEL = "Diagnózis törlése";

	// DeletePatient.java
	public static String DELETE_PATIENT_TITLE = "Páciens eltávolítása";
	public static String CONFIRM_DELETE_PATIENT = "Biztosan ki szeretné törölni az alábbi pácienst?";
	public static String CONFIRM_LABEL = "Megerősítés";
	public static String DELETE_LABEL = "Eltávolítás";
	
	// EditPatient.java
	public static String EDIT_PATIENT_TITLE = "Páciens adatainak módosítása TAJ szám alapján";
	public static String EDIT_PATIENT_BTN = "Módosítás";
	public static String PATIENT_EDIT_SUCESS = "Páciens adatai sikeresen módosítva!";

	// ListExport.java ViewMedicalHistory.java
	public static String VIEW_MEDICAL_HISTORY_TITLE = "Páciens kórtörténetének lekérdezése";
	public static String PATIENT_DATA_LABEL = "Páciens személyes adatai";
	public static String PATIENT_LIST_LOADED = "Páciens lista betöltve!";
	public static String PATIENT_LIST = "Páciens lista";
	public static String PATIENT_EXPORT_BTN = "Exportálás txt-be";
	public static String NO_PATIENT_LIST = "Nem rögzített egy pácienst sem!";
	public static String EXPORT_SUCCESS = "A páciensek személyes adatai sikeresen ki lettek mentve a(z) 'patients_export.txt' fájlba!";

}
