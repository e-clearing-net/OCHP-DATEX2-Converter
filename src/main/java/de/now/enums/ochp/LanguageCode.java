package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum LanguageCode {

    // ISO 639-3 Code / ISO 639-1 Code /  English name of Language
    AAR("aar", "aa", "Afar"),
    ABK("abk", "ab", "Abkhazian"),
    AFR("afr", "af", "Afrikaans"),
    AKA("aka", "ak", "Akan"),
    AMH("amh", "am", "Amharic"),
    ARA("ara", "ar", "Arabic"),
    ARG("arg", "an", "Aragonese"),
    ASM("asm", "as", "Assamese"),
    AVA("ava", "av", "Avaric"),
    AVE("ave", "ae", "Avestan"),
    AYM("aym", "ay", "Aymara"),
    AZE("aze", "az", "Azerbaijani"),
    BAK("bak", "ba", "Bashkir"),
    BAM("bam", "bm", "Bambara"),
    BEL("bel", "be", "Belarusian"),
    BEN("ben", "bn", "Bengali"),
    BIS("bis", "bi", "Bislama"),
    BOD("bod", "bo", "Tibetan"),
    BOS("bos", "bs", "Bosnian"),
    BRE("bre", "br", "Breton"),
    BUL("bul", "bg", "Bulgarian"),
    CAT("cat", "ca", "Catalan, Valencian"),
    CES("ces", "cs", "Czech"),
    CHA("cha", "ch", "Chamorro"),
    CHE("che", "ce", "Chechen"),
    CHU("chu", "cu", "Church Slavic, Church Slavonic, Old Bulgarian, Old Church Slavonic, Old Slavonic"),
    CHV("chv", "cv", "Chuvash"),
    COR("cor", "kw", "Cornish"),
    COS("cos", "co", "Corsican"),
    CRE("cre", "cr", "Cree"),
    CYM("cym", "cy", "Welsh"),
    DAN("dan", "da", "Danish"),
    DEU("deu", "de", "German"),
    DIV("div", "dv", "Dhivehi, Divehi, Maldivian"),
    DZO("dzo", "dz", "Dzongkha"),
    ELL("ell", "el", "Modern Greek (1453-)"),
    ENG("eng", "en", "English"),
    EPO("epo", "eo", "Esperanto"),
    EST("est", "et", "Estonian"),
    EUS("eus", "eu", "Basque"),
    EWE("ewe", "ee", "Ewe"),
    FAO("fao", "fo", "Faroese"),
    FAS("fas", "fa", "Persian"),
    FIJ("fij", "fj", "Fijian"),
    FIN("fin", "fi", "Finnish"),
    FRA("fra", "fr", "French"),
    FRY("fry", "fy", "Western Frisian"),
    FUL("ful", "ff", "Fulah"),
    GLA("gla", "gd", "Gaelic, Scottish Gaelic"),
    GLE("gle", "ga", "Irish"),
    GLG("glg", "gl", "Galician"),
    GLV("glv", "gv", "Manx"),
    GRN("grn", "gn", "Guarani"),
    GUJ("guj", "gu", "Gujarati"),
    HAT("hat", "ht", "Haitian, Haitian Creole"),
    HAU("hau", "ha", "Hausa"),
    HBS("hbs", "sh (deprecated)", "Serbo-Croatian"),
    HEB("heb", "he", "Hebrew"),
    HER("her", "hz", "Herero"),
    HIN("hin", "hi", "Hindi"),
    HMO("hmo", "ho", "Hiri Motu"),
    HRV("hrv", "hr", "Croatian"),
    HUN("hun", "hu", "Hungarian"),
    HYE("hye", "hy", "Armenian"),
    IBO("ibo", "ig", "Igbo"),
    ISL("isl", "is", "Icelandic"),
    IDO("ido", "io", "Ido"),
    III("iii", "ii", "Nuosu, Sichuan Yi"),
    IKU("iku", "iu", "Inuktitut"),
    ILE("ile", "ie", "Interlingue, Occidental"),
    INA("ina", "ia", "Interlingua (International Auxiliary Language Association)"),
    IND("ind", "id", "Indonesian"),
    IPK("ipk", "ik", "Inupiaq"),
    ITA("ita", "it", "Italian"),
    JAV("jav", "jv", "Javanese"),
    JPN("jpn", "ja", "Japanese"),
    KAL("kal", "kl", "Greenlandic, Kalaallisut"),
    KAN("kan", "kn", "Kannada"),
    KAS("kas", "ks", "Kashmiri"),
    KAT("kat", "ka", "Georgian"),
    KAU("kau", "kr", "Kanuri"),
    KAZ("kaz", "kk", "Kazakh"),
    KHM("khm", "km", "Central Khmer, Khmer"),
    KIK("kik", "ki", "Gikuyu, Kikuyu"),
    KIN("kin", "rw", "Kinyarwanda"),
    KIR("kir", "ky", "Kirghiz, Kyrgyz"),
    KOM("kom", "kv", "Komi"),
    KON("kon", "kg", "Kongo"),
    KOR("kor", "ko", "Korean"),
    KUA("kua", "kj", "Kuanyama, Kwanyama"),
    KUR("kur", "ku", "Kurdish"),
    LAO("lao", "lo", "Lao"),
    LAT("lat", "la", "Latin"),
    LAV("lav", "lv", "Latvian"),
    LIM("lim", "li", "Limburgan, Limburger, Limburgish"),
    LIN("lin", "ln", "Lingala"),
    LIT("lit", "lt", "Lithuanian"),
    LTZ("ltz", "lb", "Letzeburgesch, Luxembourgish"),
    LUB("lub", "lu", "Luba-Katanga"),
    LUG("lug", "lg", "Ganda"),
    MKD("mkd", "mk", "Macedonian"),
    MAH("mah", "mh", "Marshallese"),
    MAL("mal", "ml", "Malayalam"),
    MRI("mri", "mi", "Maori"),
    MAR("mar", "mr", "Marathi"),
    MSA("msa", "ms", "Malay (macrolanguage)"),
    MLG("mlg", "mg", "Malagasy"),
    MLT("mlt", "mt", "Maltese"),
    MON("mon", "mn", "Mongolian"),
    MYA("mya", "my", "Burmese"),
    NAU("nau", "na", "Nauru"),
    NAV("nav", "nv", "Navaho, Navajo"),
    NBL("nbl", "nr", "South Ndebele"),
    NDE("nde", "nd", "North Ndebele"),
    NDO("ndo", "ng", "Ndonga"),
    NEP("nep", "ne", "Nepali (macrolanguage)"),
    NLD("nld", "nl", "Dutch, Flemish"),
    NNO("nno", "nn", "Norwegian Nynorsk"),
    NOB("nob", "nb", "Norwegian Bokmål"),
    NOR("nor", "no", "Norwegian"),
    NYA("nya", "ny", "Chewa, Chichewa, Nyanja"),
    OCI("oci", "oc", "Occitan (post 1500)"),
    OJI("oji", "oj", "Ojibwa"),
    ORI("ori", "or", "Oriya (macrolanguage)"),
    ORM("orm", "om", "Oromo"),
    OSS("oss", "os", "Ossetian, Ossetic"),
    PAN("pan", "pa", "Panjabi, Punjabi"),
    PLI("pli", "pi", "Pali"),
    POL("pol", "pl", "Polish"),
    POR("por", "pt", "Portuguese"),
    PUS("pus", "ps", "Pashto, Pushto"),
    QUE("que", "qu", "Quechua"),
    ROH("roh", "rm", "Romansh"),
    RON("ron", "ro", "Moldavian, Moldovan, Romanian"),
    RUN("run", "rn", "Rundi"),
    RUS("rus", "ru", "Russian"),
    SAG("sag", "sg", "Sango"),
    SAN("san", "sa", "Sanskrit"),
    SIN("sin", "si", "Sinhala, Sinhalese"),
    SLK("slk", "sk", "Slovak"),
    SLV("slv", "sl", "Slovenian"),
    SME("sme", "se", "Northern Sami"),
    SMO("smo", "sm", "Samoan"),
    SNA("sna", "sn", "Shona"),
    SND("snd", "sd", "Sindhi"),
    SOM("som", "so", "Somali"),
    SOT("sot", "st", "Southern Sotho"),
    SPA("spa", "es", "Castilian, Spanish"),
    SQI("sqi", "sq", "Albanian"),
    SRD("srd", "sc", "Sardinian"),
    SRP("srp", "sr", "Serbian"),
    SSW("ssw", "ss", "Swati"),
    SUN("sun", "su", "Sundanese"),
    SWA("swa", "sw", "Swahili (macrolanguage)"),
    SWE("swe", "sv", "Swedish"),
    TAH("tah", "ty", "Tahitian"),
    TAM("tam", "ta", "Tamil"),
    TAT("tat", "tt", "Tatar"),
    TEL("tel", "te", "Telugu"),
    TGK("tgk", "tg", "Tajik"),
    TGL("tgl", "tl", "Tagalog"),
    THA("tha", "th", "Thai"),
    TIR("tir", "ti", "Tigrinya"),
    TON("ton", "to", "Tonga (Tonga Islands)"),
    TSN("tsn", "tn", "Tswana"),
    TSO("tso", "ts", "Tsonga"),
    TUK("tuk", "tk", "Turkmen"),
    TUR("tur", "tr", "Turkish"),
    TWI("twi", "tw", "Twi"),
    UIG("uig", "ug", "Uighur, Uyghur"),
    UKR("ukr", "uk", "Ukrainian"),
    URD("urd", "ur", "Urdu"),
    UZB("uzb", "uz", "Uzbek"),
    VEN("ven", "ve", "Venda"),
    VIE("vie", "vi", "Vietnamese"),
    VOL("vol", "vo", "Volapük"),
    WLN("wln", "wa", "Walloon"),
    WOL("wol", "wo", "Wolof"),
    XHO("xho", "xh", "Xhosa"),
    YID("yid", "yi", "Yiddish"),
    YOR("yor", "yo", "Yoruba"),
    ZHA("zha", "za", "Chuang, Zhuang"),
    ZHO("zho", "zh", "Chinese"),
    ZUL("zul", "zu", "Zulu");


    LanguageCode(String alpha3code, String alpha2code, String englishName) {
        this.alpha3code = alpha3code;
        this.alpha2code = alpha2code;
        this.englishName = englishName;
    }

    private final String alpha3code;
    private final String alpha2code;
    private final String englishName;

    public String getAlpha3code() {
        return alpha3code;
    }

    public String getAlpha2code() {
        return alpha2code;
    }

    public String getEnglishName() {
        return englishName;
    }

    private static final Map<String, LanguageCode> CACHE = new HashMap<>();

    static {
        for (LanguageCode languageCode : LanguageCode.values()) {
            CACHE.put(languageCode.getAlpha2code(), languageCode);
            CACHE.put(languageCode.getAlpha3code(), languageCode);
        }
    }

    public static LanguageCode fromLanguageCode2or3(final String value) {
        if (value == null) {
            return null;
        }
        final String lowerCase = value.toLowerCase();
        return CACHE.get(lowerCase);
    }

    public static String convertLanguageCode3to2(final String value) {
        final LanguageCode languageCode = fromLanguageCode2or3(value);
        return languageCode != null ? languageCode.getAlpha2code() : null;
    }


}
