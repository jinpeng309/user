package com.capslock.rpc.service.user.extractor;

import com.capslock.rpc.api.user.model.Contact;
import com.capslock.rpc.service.user.repository.mapper.model.ContactCacheData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alvin.
 */
@Component
public class ContactCacheDataExtractor {
    public ContactCacheData extract(final Contact contact) {
        return new ContactCacheData(contact.getName(), contact.getEncryptedPhoneNumber());
    }

    public List<ContactCacheData> extract(final List<Contact> contacts) {
        return contacts.stream().map(this::extract).collect(Collectors.toList());
    }
}
