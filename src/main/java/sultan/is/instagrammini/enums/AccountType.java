package sultan.is.instagrammini.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import sultan.is.instagrammini.exceptions.AccountTypeNotFoundException;

public enum AccountType {
    USER("Обычный пользователь"),
    MODERATOR("Модератор"),
    ADMIN("Администратор"),
    BUSINESS("Бизнес-аккаунт"),
    CREATOR("Контент-мейкер"),
    VERIFIED("Верифицированный пользователь");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static AccountType fromString(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new AccountTypeNotFoundException("Неизвестный тип аккаунта: " + value);
    }
}
