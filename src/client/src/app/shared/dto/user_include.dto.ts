import {UserDto} from "./user.dto";

export interface UserIncludeDto extends UserDto{
  include: boolean;
}