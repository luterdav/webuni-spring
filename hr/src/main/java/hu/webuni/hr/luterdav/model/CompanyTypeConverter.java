package hu.webuni.hr.luterdav.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import io.netty.handler.codec.http.multipart.Attribute;

@Converter(autoApply = true)
public class CompanyTypeConverter implements AttributeConverter<CompanyType, String>  {

	@Override
	public String convertToDatabaseColumn(CompanyType attribute) {
		if (attribute == null) {
            return null;
        }
		return attribute.name();
	}

	@Override
	public CompanyType convertToEntityAttribute(String dbData) {
		if (dbData == null) {
            return null;
        }

        return CompanyType.valueOf(dbData);
	}

	
}
