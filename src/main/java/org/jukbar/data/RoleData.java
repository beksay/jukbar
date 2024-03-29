package org.jukbar.data;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import org.jukbar.domain.Role;
import org.jukbar.service.RoleService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@RequestScoped
public class RoleData extends BaseData<RoleService, Role, Integer> {

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Role role) {
        retrieveAllMembersOrderedByName();
    }
    
    @Override
    protected void initFilter() {
    	list = new ArrayList<>();
    }

}
