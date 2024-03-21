package Triju.ExpenSensei.Mapper;


public abstract class BaseMapper<D, E>  {

    public abstract D toDto(E entity);

    public abstract E toEntity(D dto);
}
