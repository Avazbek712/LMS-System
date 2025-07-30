package uz.imv.lmssystem.enums;

public enum ExpenseCategoryEnum {

    // --- ЗАРПЛАТА И ВЫПЛАТЫ ПЕРСОНАЛУ ---
    SALARY_TEACHER("Зарплата преподавателям", true),
    SALARY_ADMINISTRATOR("Зарплата администраторам", true),
    SALARY_SUPPORT_STAFF("Зарплата прочему персоналу (уборка, охрана)", true),
    BONUS_PAYMENTS("Бонусы и премии персоналу", true),
    CONTRACTOR_PAYMENTS("Выплаты подрядчикам (приходящие преподаватели, фрилансеры)", true),

    // --- АРЕНДА И КОММУНАЛЬНЫЕ УСЛУГИ ---
    RENT_OFFICE("Аренда помещения", false),
    UTILITIES_ELECTRICITY("Коммунальные услуги: Электричество", false),
    UTILITIES_WATER("Коммунальные услуги: Вода и канализация", false),
    UTILITIES_HEATING("Коммунальные услуги: Отопление", false),
    UTILITIES_INTERNET("Интернет и связь", false),

    // --- МАРКЕТИНГ И РЕКЛАМА ---
    ADVERTISING_ONLINE("Онлайн-реклама (Google, соцсети)", false),
    ADVERTISING_OFFLINE("Офлайн-реклама (листовки, баннеры)", false),
    ADVERTISING_EVENTS("Расходы на мероприятия (дни открытых дверей)", false),
    SEO_AND_SMM("Услуги по SEO и SMM", false),

    // --- АДМИНИСТРАТИВНЫЕ И ОФИСНЫЕ РАСХОДЫ ---
    OFFICE_SUPPLIES("Канцелярские товары", false),
    OFFICE_EQUIPMENT_PURCHASE("Покупка офисной техники (компьютеры, принтеры)", false),
    OFFICE_EQUIPMENT_MAINTENANCE("Обслуживание и ремонт техники", false),
    FURNITURE_PURCHASE("Покупка мебели", false),
    SOFTWARE_LICENSES("Программное обеспечение и подписки (CRM, Zoom, и т.д.)", false),
    BANKING_FEES("Банковские комиссии и обслуживание счета", false),

    // --- УЧЕБНЫЕ МАТЕРИАЛЫ И РАСХОДНИКИ ---
    EDUCATIONAL_MATERIALS("Покупка учебников и методических пособий", false),
    CONSUMABLES_FOR_CLASSES("Расходные материалы для занятий (маркеры, бумага)", false),
    PLATFORM_FEES("Оплата учебных онлайн-платформ", false),

    // --- НАЛОГИ И ОБЯЗАТЕЛЬНЫЕ ПЛАТЕЖИ ---
    TAXES("Налоги", false),
    GOVERNMENT_FEES("Государственные пошлины и сборы", false),

    // --- ПРОЧИЕ РАСХОДЫ ---
    HOSPITALITY("Представительские расходы (кофе, вода для клиентов и сотрудников)", false),
    STAFF_TRAINING("Обучение и повышение квалификации сотрудников", false),
    TRANSPORTATION("Транспортные и командировочные расходы", false),
    UNFORESEEN_EXPENSES("Непредвиденные расходы", false),
    OTHER("Прочее", false);


    ExpenseCategoryEnum(String description, boolean isSalaryRelated) {
        // Флаг, чтобы отличать зарплатные расходы от прочих
    }

    // Конструктор для не-зарплатных категорий для удобства
    ExpenseCategoryEnum(String description) {
        this(description, false);
    }

}
