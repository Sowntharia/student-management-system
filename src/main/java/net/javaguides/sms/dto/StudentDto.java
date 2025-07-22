package net.javaguides.sms.dto;

public class StudentDto extends BasePersonDto {

    private Long id;

    public StudentDto() {
        super();
    }

    public StudentDto(Long id, String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.id = id;
    }

    public StudentDto(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
