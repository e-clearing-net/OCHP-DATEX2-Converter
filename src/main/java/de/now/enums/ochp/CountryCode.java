package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

/**
 * Data source https://www.iso.org/obp/ui/#search 249 countries (https://www.iso.org/iso-3166-country-codes.html)
 * Updated Date 2022-02-21
 */
public enum CountryCode {

    AFGHANISTAN("Afghanistan", "AF", "AFG", 4),
    ALBANIA("Albania", "AL", "ALB", 8),
    ALGERIA("Algeria", "DZ", "DZA", 12),
    AMERICAN_SAMOA("American Samoa", "AS", "ASM", 16),
    ANDORRA("Andorra", "AD", "AND", 20),
    ANGOLA("Angola", "AO", "AGO", 24),
    ANGUILLA("Anguilla", "AI", "AIA", 660),
    ANTARCTICA("Antarctica", "AQ", "ATA", 10),
    ANTIGUA_AND_BARBUDA("Antigua and Barbuda", "AG", "ATG", 28),
    ARGENTINA("Argentina", "AR", "ARG", 32),
    ARMENIA("Armenia", "AM", "ARM", 51),
    ARUBA("Aruba", "AW", "ABW", 533),
    AUSTRALIA("Australia", "AU", "AUS", 36),
    AUSTRIA("Austria", "AT", "AUT", 40),
    AZERBAIJAN("Azerbaijan", "AZ", "AZE", 31),
    BAHAMAS_THE("Bahamas (the)", "BS", "BHS", 44),
    BAHRAIN("Bahrain", "BH", "BHR", 48),
    BANGLADESH("Bangladesh", "BD", "BGD", 50),
    BARBADOS("Barbados", "BB", "BRB", 52),
    BELARUS("Belarus", "BY", "BLR", 112),
    BELGIUM("Belgium", "BE", "BEL", 56),
    BELIZE("Belize", "BZ", "BLZ", 84),
    BENIN("Benin", "BJ", "BEN", 204),
    BERMUDA("Bermuda", "BM", "BMU", 60),
    ALAND_ISLANDS("Åland Islands", "AX", "ALA", 248),
    BHUTAN("Bhutan", "BT", "BTN", 64),
    BOLIVIA_PLURINATIONAL_STATE_OF("Bolivia (Plurinational State of)", "BO", "BOL", 68),
    BONAIRE_SINT_EUSTATIUS_AND_SABA("Bonaire, Sint Eustatius and Saba", "BQ", "BES", 535),
    BOSNIA_AND_HERZEGOVINA("Bosnia and Herzegovina", "BA", "BIH", 70),
    BOTSWANA("Botswana", "BW", "BWA", 72),
    BOUVET_ISLAND("Bouvet Island", "BV", "BVT", 74),
    BRAZIL("Brazil", "BR", "BRA", 76),
    BRITISH_INDIAN_OCEAN_TERRITORY_THE("British Indian Ocean Territory (the)", "IO", "IOT", 86),
    BRUNEI_DARUSSALAM("Brunei Darussalam", "BN", "BRN", 96),
    BULGARIA("Bulgaria", "BG", "BGR", 100),
    BURKINA_FASO("Burkina Faso", "BF", "BFA", 854),
    BURUNDI("Burundi", "BI", "BDI", 108),
    CABO_VERDE("Cabo Verde", "CV", "CPV", 132),
    CAMBODIA("Cambodia", "KH", "KHM", 116),
    CAMEROON("Cameroon", "CM", "CMR", 120),
    CANADA("Canada", "CA", "CAN", 124),
    CAYMAN_ISLANDS_THE("Cayman Islands (the)", "KY", "CYM", 136),
    CENTRAL_AFRICAN_REPUBLIC_THE("Central African Republic (the)", "CF", "CAF", 140),
    CHAD("Chad", "TD", "TCD", 148),
    CHILE("Chile", "CL", "CHL", 152),
    CHINA("China", "CN", "CHN", 156),
    CHRISTMAS_ISLAND("Christmas Island", "CX", "CXR", 162),
    COCOS_KEELING_ISLANDS_THE("Cocos (Keeling) Islands (the)", "CC", "CCK", 166),
    COLOMBIA("Colombia", "CO", "COL", 170),
    COMOROS_THE("Comoros (the)", "KM", "COM", 174),
    CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE("Congo (the Democratic Republic of the)", "CD", "COD", 180),
    CONGO_THE("Congo (the)", "CG", "COG", 178),
    COOK_ISLANDS_THE("Cook Islands (the)", "CK", "COK", 184),
    COSTA_RICA("Costa Rica", "CR", "CRI", 188),
    CROATIA("Croatia", "HR", "HRV", 191),
    CUBA("Cuba", "CU", "CUB", 192),
    CURACAO("Curaçao", "CW", "CUW", 531),
    CYPRUS("Cyprus", "CY", "CYP", 196),
    CZECHIA("Czechia", "CZ", "CZE", 203),
    COTE_D_IVOIRE("Côte d'Ivoire", "CI", "CIV", 384),
    DENMARK("Denmark", "DK", "DNK", 208),
    DJIBOUTI("Djibouti", "DJ", "DJI", 262),
    DOMINICA("Dominica", "DM", "DMA", 212),
    DOMINICAN_REPUBLIC_THE("Dominican Republic (the)", "DO", "DOM", 214),
    ECUADOR("Ecuador", "EC", "ECU", 218),
    EGYPT("Egypt", "EG", "EGY", 818),
    EL_SALVADOR("El Salvador", "SV", "SLV", 222),
    EQUATORIAL_GUINEA("Equatorial Guinea", "GQ", "GNQ", 226),
    ERITREA("Eritrea", "ER", "ERI", 232),
    ESTONIA("Estonia", "EE", "EST", 233),
    ESWATINI("Eswatini", "SZ", "SWZ", 748),
    ETHIOPIA("Ethiopia", "ET", "ETH", 231),
    FALKLAND_ISLANDS_THE__MALVINAS("Falkland Islands (the) [Malvinas]", "FK", "FLK", 238),
    FAROE_ISLANDS_THE("Faroe Islands (the)", "FO", "FRO", 234),
    FIJI("Fiji", "FJ", "FJI", 242),
    FINLAND("Finland", "FI", "FIN", 246),
    FRANCE("France", "FR", "FRA", 250),
    FRENCH_GUIANA("French Guiana", "GF", "GUF", 254),
    FRENCH_POLYNESIA("French Polynesia", "PF", "PYF", 258),
    FRENCH_SOUTHERN_TERRITORIES_THE("French Southern Territories (the)", "TF", "ATF", 260),
    GABON("Gabon", "GA", "GAB", 266),
    GAMBIA_THE("Gambia (the)", "GM", "GMB", 270),
    GEORGIA("Georgia", "GE", "GEO", 268),
    GERMANY("Germany", "DE", "DEU", 276),
    GHANA("Ghana", "GH", "GHA", 288),
    GIBRALTAR("Gibraltar", "GI", "GIB", 292),
    GREECE("Greece", "GR", "GRC", 300),
    GREENLAND("Greenland", "GL", "GRL", 304),
    GRENADA("Grenada", "GD", "GRD", 308),
    GUADELOUPE("Guadeloupe", "GP", "GLP", 312),
    GUAM("Guam", "GU", "GUM", 316),
    GUATEMALA("Guatemala", "GT", "GTM", 320),
    GUERNSEY("Guernsey", "GG", "GGY", 831),
    GUINEA("Guinea", "GN", "GIN", 324),
    GUINEA_BISSAU("Guinea-Bissau", "GW", "GNB", 624),
    GUYANA("Guyana", "GY", "GUY", 328),
    HAITI("Haiti", "HT", "HTI", 332),
    HEARD_ISLAND_AND_MCDONALD_ISLANDS("Heard Island and McDonald Islands", "HM", "HMD", 334),
    HOLY_SEE_THE("Holy See (the)", "VA", "VAT", 336),
    HONDURAS("Honduras", "HN", "HND", 340),
    HONG_KONG("Hong Kong", "HK", "HKG", 344),
    HUNGARY("Hungary", "HU", "HUN", 348),
    ICELAND("Iceland", "IS", "ISL", 352),
    INDIA("India", "IN", "IND", 356),
    INDONESIA("Indonesia", "ID", "IDN", 360),
    IRAN_ISLAMIC_REPUBLIC_OF("Iran (Islamic Republic of)", "IR", "IRN", 364),
    IRAQ("Iraq", "IQ", "IRQ", 368),
    IRELAND("Ireland", "IE", "IRL", 372),
    ISLE_OF_MAN("Isle of Man", "IM", "IMN", 833),
    ISRAEL("Israel", "IL", "ISR", 376),
    ITALY("Italy", "IT", "ITA", 380),
    JAMAICA("Jamaica", "JM", "JAM", 388),
    JAPAN("Japan", "JP", "JPN", 392),
    JERSEY("Jersey", "JE", "JEY", 832),
    JORDAN("Jordan", "JO", "JOR", 400),
    KAZAKHSTAN("Kazakhstan", "KZ", "KAZ", 398),
    KENYA("Kenya", "KE", "KEN", 404),
    KIRIBATI("Kiribati", "KI", "KIR", 296),
    KOREA_THE_DEMOCRATIC_PEOPLE_S_REPUBLIC_OF("Korea (the Democratic People's Republic of)", "KP", "PRK", 408),
    KOREA_THE_REPUBLIC_OF("Korea (the Republic of)", "KR", "KOR", 410),
    KUWAIT("Kuwait", "KW", "KWT", 414),
    KYRGYZSTAN("Kyrgyzstan", "KG", "KGZ", 417),
    LAO_PEOPLE_S_DEMOCRATIC_REPUBLIC_THE("Lao People's Democratic Republic (the)", "LA", "LAO", 418),
    LATVIA("Latvia", "LV", "LVA", 428),
    LEBANON("Lebanon", "LB", "LBN", 422),
    LESOTHO("Lesotho", "LS", "LSO", 426),
    LIBERIA("Liberia", "LR", "LBR", 430),
    LIBYA("Libya", "LY", "LBY", 434),
    LIECHTENSTEIN("Liechtenstein", "LI", "LIE", 438),
    LITHUANIA("Lithuania", "LT", "LTU", 440),
    LUXEMBOURG("Luxembourg", "LU", "LUX", 442),
    MACAO("Macao", "MO", "MAC", 446),
    MADAGASCAR("Madagascar", "MG", "MDG", 450),
    MALAWI("Malawi", "MW", "MWI", 454),
    MALAYSIA("Malaysia", "MY", "MYS", 458),
    MALDIVES("Maldives", "MV", "MDV", 462),
    MALI("Mali", "ML", "MLI", 466),
    MALTA("Malta", "MT", "MLT", 470),
    MARSHALL_ISLANDS_THE("Marshall Islands (the)", "MH", "MHL", 584),
    MARTINIQUE("Martinique", "MQ", "MTQ", 474),
    MAURITANIA("Mauritania", "MR", "MRT", 478),
    MAURITIUS("Mauritius", "MU", "MUS", 480),
    MAYOTTE("Mayotte", "YT", "MYT", 175),
    MEXICO("Mexico", "MX", "MEX", 484),
    MICRONESIA_FEDERATED_STATES_OF("Micronesia (Federated States of)", "FM", "FSM", 583),
    MOLDOVA_THE_REPUBLIC_OF("Moldova (the Republic of)", "MD", "MDA", 498),
    MONACO("Monaco", "MC", "MCO", 492),
    MONGOLIA("Mongolia", "MN", "MNG", 496),
    MONTENEGRO("Montenegro", "ME", "MNE", 499),
    MONTSERRAT("Montserrat", "MS", "MSR", 500),
    MOROCCO("Morocco", "MA", "MAR", 504),
    MOZAMBIQUE("Mozambique", "MZ", "MOZ", 508),
    MYANMAR("Myanmar", "MM", "MMR", 104),
    NAMIBIA("Namibia", "NA", "NAM", 516),
    NAURU("Nauru", "NR", "NRU", 520),
    NEPAL("Nepal", "NP", "NPL", 524),
    NETHERLANDS_THE("Netherlands (the)", "NL", "NLD", 528),
    NEW_CALEDONIA("New Caledonia", "NC", "NCL", 540),
    NEW_ZEALAND("New Zealand", "NZ", "NZL", 554),
    NICARAGUA("Nicaragua", "NI", "NIC", 558),
    NIGER_THE("Niger (the)", "NE", "NER", 562),
    NIGERIA("Nigeria", "NG", "NGA", 566),
    NIUE("Niue", "NU", "NIU", 570),
    NORFOLK_ISLAND("Norfolk Island", "NF", "NFK", 574),
    NORTH_MACEDONIA("North Macedonia", "MK", "MKD", 807),
    NORTHERN_MARIANA_ISLANDS_THE("Northern Mariana Islands (the)", "MP", "MNP", 580),
    NORWAY("Norway", "NO", "NOR", 578),
    OMAN("Oman", "OM", "OMN", 512),
    PAKISTAN("Pakistan", "PK", "PAK", 586),
    PALAU("Palau", "PW", "PLW", 585),
    PALESTINE_STATE_OF("Palestine, State of", "PS", "PSE", 275),
    PANAMA("Panama", "PA", "PAN", 591),
    PAPUA_NEW_GUINEA("Papua New Guinea", "PG", "PNG", 598),
    PARAGUAY("Paraguay", "PY", "PRY", 600),
    PERU("Peru", "PE", "PER", 604),
    PHILIPPINES_THE("Philippines (the)", "PH", "PHL", 608),
    PITCAIRN("Pitcairn", "PN", "PCN", 612),
    POLAND("Poland", "PL", "POL", 616),
    PORTUGAL("Portugal", "PT", "PRT", 620),
    PUERTO_RICO("Puerto Rico", "PR", "PRI", 630),
    QATAR("Qatar", "QA", "QAT", 634),
    ROMANIA("Romania", "RO", "ROU", 642),
    RUSSIAN_FEDERATION_THE("Russian Federation (the)", "RU", "RUS", 643),
    RWANDA("Rwanda", "RW", "RWA", 646),
    REUNION("Réunion", "RE", "REU", 638),
    SAINT_BARTHELEMY("Saint Barthélemy", "BL", "BLM", 652),
    SAINT_HELENA_ASCENSION_AND_TRISTAN_DA_CUNHA("Saint Helena, Ascension and Tristan da Cunha", "SH", "SHN", 654),
    SAINT_KITTS_AND_NEVIS("Saint Kitts and Nevis", "KN", "KNA", 659),
    SAINT_LUCIA("Saint Lucia", "LC", "LCA", 662),
    SAINT_MARTIN_FRENCH_PART("Saint Martin (French part)", "MF", "MAF", 663),
    SAINT_PIERRE_AND_MIQUELON("Saint Pierre and Miquelon", "PM", "SPM", 666),
    SAINT_VINCENT_AND_THE_GRENADINES("Saint Vincent and the Grenadines", "VC", "VCT", 670),
    SAMOA("Samoa", "WS", "WSM", 882),
    SAN_MARINO("San Marino", "SM", "SMR", 674),
    SAO_TOME_AND_PRINCIPE("Sao Tome and Principe", "ST", "STP", 678),
    SAUDI_ARABIA("Saudi Arabia", "SA", "SAU", 682),
    SENEGAL("Senegal", "SN", "SEN", 686),
    SERBIA("Serbia", "RS", "SRB", 688),
    SEYCHELLES("Seychelles", "SC", "SYC", 690),
    SIERRA_LEONE("Sierra Leone", "SL", "SLE", 694),
    SINGAPORE("Singapore", "SG", "SGP", 702),
    SINT_MAARTEN_DUTCH_PART("Sint Maarten (Dutch part)", "SX", "SXM", 534),
    SLOVAKIA("Slovakia", "SK", "SVK", 703),
    SLOVENIA("Slovenia", "SI", "SVN", 705),
    SOLOMON_ISLANDS("Solomon Islands", "SB", "SLB", 90),
    SOMALIA("Somalia", "SO", "SOM", 706),
    SOUTH_AFRICA("South Africa", "ZA", "ZAF", 710),
    SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("South Georgia and the South Sandwich Islands", "GS", "SGS", 239),
    SOUTH_SUDAN("South Sudan", "SS", "SSD", 728),
    SPAIN("Spain", "ES", "ESP", 724),
    SRI_LANKA("Sri Lanka", "LK", "LKA", 144),
    SUDAN_THE("Sudan (the)", "SD", "SDN", 729),
    SURINAME("Suriname", "SR", "SUR", 740),
    SVALBARD_AND_JAN_MAYEN("Svalbard and Jan Mayen", "SJ", "SJM", 744),
    SWEDEN("Sweden", "SE", "SWE", 752),
    SWITZERLAND("Switzerland", "CH", "CHE", 756),
    SYRIAN_ARAB_REPUBLIC_THE("Syrian Arab Republic (the)", "SY", "SYR", 760),
    TAIWAN_PROVINCE_OF_CHINA("Taiwan (Province of China)", "TW", "TWN", 158),
    TAJIKISTAN("Tajikistan", "TJ", "TJK", 762),
    TANZANIA_THE_UNITED_REPUBLIC_OF("Tanzania, the United Republic of", "TZ", "TZA", 834),
    THAILAND("Thailand", "TH", "THA", 764),
    TIMOR_LESTE("Timor-Leste", "TL", "TLS", 626),
    TOGO("Togo", "TG", "TGO", 768),
    TOKELAU("Tokelau", "TK", "TKL", 772),
    TONGA("Tonga", "TO", "TON", 776),
    TRINIDAD_AND_TOBAGO("Trinidad and Tobago", "TT", "TTO", 780),
    TUNISIA("Tunisia", "TN", "TUN", 788),
    TURKEY("Turkey", "TR", "TUR", 792),
    TURKMENISTAN("Turkmenistan", "TM", "TKM", 795),
    TURKS_AND_CAICOS_ISLANDS_THE("Turks and Caicos Islands (the)", "TC", "TCA", 796),
    TUVALU("Tuvalu", "TV", "TUV", 798),
    UGANDA("Uganda", "UG", "UGA", 800),
    UKRAINE("Ukraine", "UA", "UKR", 804),
    UNITED_ARAB_EMIRATES_THE("United Arab Emirates (the)", "AE", "ARE", 784),
    UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND_THE("United Kingdom of Great Britain and Northern Ireland (the)", "GB", "GBR", 826),
    UNITED_STATES_MINOR_OUTLYING_ISLANDS_THE("United States Minor Outlying Islands (the)", "UM", "UMI", 581),
    UNITED_STATES_OF_AMERICA_THE("United States of America (the)", "US", "USA", 840),
    URUGUAY("Uruguay", "UY", "URY", 858),
    UZBEKISTAN("Uzbekistan", "UZ", "UZB", 860),
    VANUATU("Vanuatu", "VU", "VUT", 548),
    VENEZUELA_BOLIVARIAN_REPUBLIC_OF("Venezuela (Bolivarian Republic of)", "VE", "VEN", 862),
    VIET_NAM("Viet Nam", "VN", "VNM", 704),
    VIRGIN_ISLANDS_BRITISH("Virgin Islands (British)", "VG", "VGB", 92),
    VIRGIN_ISLANDS_US("Virgin Islands (U.S.)", "VI", "VIR", 850),
    WALLIS_AND_FUTUNA("Wallis and Futuna", "WF", "WLF", 876),
    WESTERN_SAHARA("Western Sahara*", "EH", "ESH", 732),
    YEMEN("Yemen", "YE", "YEM", 887),
    ZAMBIA("Zambia", "ZM", "ZMB", 894),
    ZIMBABWE("Zimbabwe", "ZW", "ZWE", 716);

    CountryCode(String englishShortName, String alpha2code, String alpha3code, int numeric) {
        this.englishShortName = englishShortName;
        this.alpha2code = alpha2code;
        this.alpha3code = alpha3code;
        this.numeric = numeric;
    }

    private final String englishShortName;
    private final String alpha2code;
    private final String alpha3code;
    private final int numeric;

    public String getEnglishShortName() {
        return englishShortName;
    }

    public String getAlpha2code() {
        return alpha2code;
    }

    public String getAlpha3code() {
        return alpha3code;
    }

    public int getNumeric() {
        return numeric;
    }

    private static final Map<String, CountryCode> CACHE = new HashMap<>();

    static {
        for (CountryCode countryCode : CountryCode.values()) {
            CACHE.put(countryCode.getAlpha2code(), countryCode);
            CACHE.put(countryCode.getAlpha3code(), countryCode);
        }
    }

    public static CountryCode fromCountryCode2or3(final String value) {
        if (value == null) {
            return null;
        }
        final String upperCase = value.toUpperCase();
        return CACHE.get(upperCase);
    }

    public static String convertCountryCode3to2(final String value) {
        final CountryCode countryCode = fromCountryCode2or3(value);
        return countryCode != null ? countryCode.getAlpha2code() : null;
    }
}
