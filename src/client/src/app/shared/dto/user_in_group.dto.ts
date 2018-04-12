import {UserDto} from "./user.dto";

export interface UserInGroupDto extends UserDto{
  include_in_draw: boolean;
  include_children_in_draw: boolean;
}
