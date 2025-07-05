package com.example.dgu_semi_erp_back.entity.auth.user;

public enum Major {
    BUDDHIST_STUDIES("불교학전공"),
    BUDDHIST_CONTENTS("불교문화콘텐츠전공"),
    MEDITATION_COUNSELING("명상심리상담학과"),
    WEB_LITERATURE("웹문예학과"),
    KOREAN_HISTORY("국사학과"),
    ARCHAEO_ART_HISTORY("고고미술사학과"),
    ENGLISH_LANGUAGE("영어영문학과"),
    JAPANESE_LANGUAGE("일어일문학과"),
    CHINESE_LANGUAGE("중어중문학과"),
    DESIGN_ART("디자인미술학과"),
    SPORTS_SCIENCE("스포츠과학전공"),
    SPORTS_MEDICINE("스포츠의학전공"),
    ELITE_SPORTS("엘리트스포츠전공"),
    LANDSCAPE_GARDEN_DESIGN("조경·정원디자인학과"),
    PUBLIC_ADMINISTRATION("공공행정학전공"),
    POLICE_ADMINISTRATION("경찰행정학전공"),
    SOCIAL_WELFARE("사회복지학과"),
    CHILD_YOUTH_EDUCATION("아동청소년교육학과"),
    AIRLINE_SERVICE_TRADE("항공서비스무역학과"),
    BUSINESS_ADMINISTRATION("경영학과"),
    ACCOUNTING_TAX("회계세무학과"),
    INFORMATION_MANAGEMENT("정보경영학과"),
    HOTEL_TOURISM("호텔관광경영학전공"),
    CULINARY_SERVICE("조리외식경영학전공"),
    EARLY_CHILDHOOD_EDUCATION("유아교육과"),
    HOME_ECONOMICS_EDUCATION("가정교육과"),
    MATH_EDUCATION("수학교육과"),
    MEDICAL_INFORMATION("보건의료정보학과"),
    BEAUTY_ART_INDUSTRY("뷰티아트산업학과"),
    BIO_CHEMICAL_CONVERGENCE("바이오·화학융합학부"),
    ELECTRONIC_COMMUNICATION("전자정보통신공학과"),
    NUCLEAR_ENERGY_ELECTRICAL("원자력·에너지·전기공학과"),
    SAFETY_HEALTH("안전보건전공"),
    FIRE_SAFETY("소방방재전공"),
    AUTO_MATERIAL_ENGINEERING("자동차소재부품공학전공"),
    COMPUTER_ENGINEERING("컴퓨터공학과"),
    ORIENTAL_MEDICINE("한의예과·한의학과"),
    MEDICINE("의예과·의학과"),
    NURSING("간호학과"),
    GLOBAL_TALENT("글로컬인재학부");

    private final String label;

    Major(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Major fromLabel(String label) {
        for (Major major : values()) {
            if (major.label.equals(label)) {
                return major;
            }
        }
        throw new IllegalArgumentException("Unknown major label: " + label);
    }
}