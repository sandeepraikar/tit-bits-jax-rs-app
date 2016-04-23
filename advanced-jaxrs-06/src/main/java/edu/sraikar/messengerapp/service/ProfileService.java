package edu.sraikar.messengerapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.sraikar.messengerapp.database.DatabaseClass;
import edu.sraikar.messengerapp.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public ProfileService(){
		profiles.put("sandeepraikar", new Profile(1L, "sandeepraikar", "Sandeep", "Raikar"));
	}
	public List<Profile> getAllProfiles() {
		return new ArrayList<>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile udateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}

}
