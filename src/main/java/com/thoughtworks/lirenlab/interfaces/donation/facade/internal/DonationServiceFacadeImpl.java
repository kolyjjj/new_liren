package com.thoughtworks.lirenlab.interfaces.donation.facade.internal;

import com.thoughtworks.lirenlab.application.DonationService;
import com.thoughtworks.lirenlab.domain.model.donation.Book;
import com.thoughtworks.lirenlab.domain.model.donation.Donation;
import com.thoughtworks.lirenlab.domain.model.donation.DonationId;
import com.thoughtworks.lirenlab.domain.model.donation.DonationRepository;
import com.thoughtworks.lirenlab.interfaces.donation.facade.DonationServiceFacade;
import com.thoughtworks.lirenlab.interfaces.donation.facade.dto.BookDTO;
import com.thoughtworks.lirenlab.interfaces.donation.facade.dto.DonationDTO;
import com.thoughtworks.lirenlab.interfaces.donation.facade.internal.assembler.DonationDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.thoughtworks.lirenlab.domain.model.device.DeviceId.deviceId;
import static com.thoughtworks.lirenlab.domain.model.donation.DonationId.donationId;

@Service
@Transactional
public class DonationServiceFacadeImpl implements DonationServiceFacade {

    private final DonationService donationService;
    private final DonationRepository donationRepository;
    private final DonationDTOAssembler assembler;

    @Autowired
    public DonationServiceFacadeImpl(final DonationService donationService, final DonationRepository donationRepository) {
        this.donationService = donationService;
        this.donationRepository = donationRepository;
        assembler = new DonationDTOAssembler();
    }

    @Override
    public List<DonationDTO> getNewDonations() {
        return assembler.toDonationDTOs(donationRepository.newDonations());
    }

    @Override
    public String newDonation(String deviceId, final List<BookDTO> bookDTOs) {
        List<Book> books = assembler.fromBookDTOs(bookDTOs);

        DonationId donationId = donationService.newDonation(deviceId(deviceId), books);
        return donationId.strValue();
    }

    @Override
    public DonationDTO getDonationById(String id) {
        Donation donation = donationRepository.find(donationId(id));
        return assembler.toDonationDTO(donation);
    }

    @Override
    public List<DonationDTO> getDonationsOfDevice(String deviceId) {
        List<Donation> donations = donationRepository.find(deviceId(deviceId));
        return assembler.toDonationDTOs(donations);
    }

}
