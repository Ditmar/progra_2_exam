package domain.usecase;

import domain.repository.StudentRepository;

public class DeleteStudentUseCase {

   private final StudentRepository repository;

   public DeleteStudentUseCase(StudentRepository repository){
    this.repository = repository;
   }
   
   public void execute(String ci){
    if(ci == null || ci.isBlank())
        throw new IllegalArgumentException("CI obligatorio");
    repository.delete(ci);
   }
}
