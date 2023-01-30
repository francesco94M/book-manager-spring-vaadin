package com.fm.bookmanager.utils;


import java.time.LocalDate;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fm.bookmanager.entity.Book;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;


public class BookViewHelper
{





    public enum FIELD
    {
        NAME("name",()-> new TextField("Name")),
        AUTHOR("author",() -> new TextField("Author")),
        PAGES("pages", () -> new TextField("Pages")),
        EAN_CODE("eanCode", () -> new TextField("Ean code")),
        PUBLICATION_DATE("publicationDate", () -> new DatePicker("Publication Date"));

        private String value;
        private Supplier<? extends Component> inputFieldSupplier;

        FIELD(String value, Supplier<? extends Component> inputFieldSupplier)
        {
            this.value = value;
            this.inputFieldSupplier = inputFieldSupplier;
        }

        public String getValue()
        {
            return value;
        }

        public Supplier<? extends Component> getInputFieldSupplier()
        {
            return inputFieldSupplier;
        }
    }
}
