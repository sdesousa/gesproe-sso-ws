package af.cmr.indyli.gespro.trans.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import af.cmr.indyli.gespro.business.dto.GpOrganizationBasicDTO;
import af.cmr.indyli.gespro.business.dto.GpOrganizationFullDTO;
import af.cmr.indyli.gespro.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.business.service.IGpOrganizationService;
import af.cmr.indyli.gespro.business.utils.GesproConstantes.GesproConstantesService;
import af.cmr.indyli.gespro.trans.urlbase.GesproUrlBase;

@CrossOrigin(origins = GesproUrlBase.url, maxAge = GesproUrlBase.maxAge)
@RestController
@RequestMapping("/organization")
public class GpOrganizationController {

	@Resource(name = GesproConstantesService.GP_ORGANIZATION_KEY)
	private IGpOrganizationService gpOrganizationService;

	/**
	 * Create Organization in the database
	 * @throws GesproBusinessException 
	 * 
	 **/
	@PostMapping
	public ResponseEntity<?> createOrganization(@Valid @RequestBody GpOrganizationFullDTO gpOrganization) throws GesproBusinessException {
		return ResponseEntity.ok(gpOrganizationService.create(gpOrganization));
	}

	/**
	 * return the list of all Organization
	 * 
	 **/
	@GetMapping
	public List<GpOrganizationBasicDTO> getAllOrganization() {

		return gpOrganizationService.findAll();
	}

	/**
	 * return Organization by id
	 * 
	 * @throws GesproBusinessException
	 **/
	@GetMapping("/{id}")
	public GpOrganizationFullDTO getOneOrganization(@PathVariable(value = "id") int id) throws GesproBusinessException {
		return this.gpOrganizationService.findById(id);
	}

	/**
	 * modify a given Organization in the database
	 * 
	 * @throws GesproBusinessException
	 * @throws AccessDeniedException
	 */

	@PutMapping("/{id}")
	public ResponseEntity<GpOrganizationBasicDTO> getUpdateGpOrganization(@PathVariable(value = "id") int idGpOrganization,
			@Valid @RequestBody GpOrganizationFullDTO organizationDetails)
			throws GesproBusinessException, AccessDeniedException {

		GpOrganizationFullDTO orgFull = gpOrganizationService.findById(idGpOrganization);
		if (orgFull == null) {
			return ResponseEntity.notFound().build();
		}

		GpOrganizationBasicDTO updateOrg = gpOrganizationService.update(organizationDetails);
		return ResponseEntity.ok().body(updateOrg);
	}

	/**
	 * Delete a given Organization in the database
	 * 
	 * @throws GesproBusinessException
	 * @throws AccessDeniedException
	 **/
	@DeleteMapping("/{id}")
	public ResponseEntity<GpOrganizationBasicDTO> deleteOrganization(@PathVariable(value = "id") int idOrganization)
			throws AccessDeniedException, GesproBusinessException {
		gpOrganizationService.deleteById(idOrganization);
		return ResponseEntity.ok().build();
	}
}
