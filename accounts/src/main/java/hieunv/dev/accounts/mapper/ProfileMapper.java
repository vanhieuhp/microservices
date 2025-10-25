package hieunv.dev.accounts.mapper;

import hieunv.dev.accounts.dto.ProfileDto;
import hieunv.dev.accounts.entity.Profile;

public class ProfileMapper {


    public static ProfileDto mapToProfileDto(Profile profile, ProfileDto profileDto) {
        profileDto.setName(profile.getName());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        return profileDto;
    }

}
